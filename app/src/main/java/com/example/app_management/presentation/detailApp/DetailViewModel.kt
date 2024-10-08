package com.example.app_management.presentation.detailApp

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AppInfo
import com.example.domain.models.AppInfoDetailState
import com.example.domain.models.toAppInfo
import com.example.domain.useCases.GetAppByPackageNameUseCase
import com.example.app_management.domain.useCases.InsertAppInfoUseCase
import com.example.app_management.presentation.apps.CoroutineContextProvider
import com.example.app_management.presentation.ui.theme.Green40
import com.example.app_management.presentation.ui.theme.Red80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getDetailUseCase: GetAppByPackageNameUseCase,
    val insertAppInfoUseCase: InsertAppInfoUseCase,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    private val _appDetail = MutableStateFlow<AppInfoDetailState?>(null)
    val appDetail: StateFlow<AppInfoDetailState?> = _appDetail.asStateFlow()

    private val _analysisPermissions = MutableStateFlow(Pair("", Green40))
    val analysisPermissions: StateFlow<Pair<String, Color>> = _analysisPermissions.asStateFlow()

    private val _analysisUsage = MutableStateFlow(Pair("", Green40))
    val analysisUsage: StateFlow<Pair<String, Color>> = _analysisUsage.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _appInfo = MutableStateFlow(AppInfo("", "", ""))
    val appInfo: StateFlow<AppInfo> = _appInfo.asStateFlow()

    init {
        savedStateHandle.get<String>("packageName")?.let { packageName ->
            getAppByPackageName(packageName)
        }
    }

    fun getAppByPackageName(packageName: String) {
        if (packageName == "") return
        viewModelScope.launch(coroutineContextProvider.backgroundContext) {
            _appDetail.value = getDetailUseCase(packageName)
            analyseUsage()
            analysePermissions()
        }
    }

    private fun analyseUsage() {
        _analysisUsage.value = when {
            _appDetail.value?.isSystemApp == true ->
                Pair(
                    "Esta aplicacion es del propio sistema operativo, por lo que es necesario para el buen funcionamiento del equipo.",
                    Green40
                )

            _appDetail.value?.isSystemApp == false && (_appDetail.value?.percentageUsage
                ?: 0.0) == 0.0 ->
                Pair(
                    "Te sugerimos que lo borres, no lo usas.",
                    Red80
                )

            _appDetail.value?.isSystemApp == false && (_appDetail.value?.percentageUsage
                ?: 0.0) < 11 ->
                Pair(
                    "Te sugerimos que lo borres, ya que su porcentaje de uso es bajo.",
                    Red80
                )

            else -> Pair("", Green40)
        }
    }

    private fun analysePermissions() {
        _analysisPermissions.value = when {
            _appDetail.value?.isSystemApp == false && (_appDetail.value?.securityPercentages
                ?: 0.0) > 50 -> Pair(
                "Te sugerimos que verifiques los permisos que le has otorgado a la app, ya que contiene permisos sensibles.",
                Red80
            )

            _appDetail.value?.isSystemApp == false && (_appDetail.value?.securityPercentages
                ?: 0.0) > 25 -> Pair(
                "Tiene pocos permisos sensibles, aun asi te recomendamos verificar si es necesario",
                Color.Yellow
            )

            _appDetail.value?.isSystemApp == false && _appDetail.value?.securityPercentages == 0.0 -> Pair(
                "No contiene permisos sensibles",
                Color.Green
            )

            _appDetail.value?.isSystemApp == true -> Pair(
                "Esta aplicacion es del mismo sistema android, por lo que es necesario los permisos para que funcione de forma correcta",
                Green40
            )

            else -> Pair("", Green40)
        }
    }

    fun updateShowDialog() {
        updateDescription(_appDetail.value?.toAppInfo())
        _showDialog.value = !_showDialog.value
    }


    fun insertAppInfo(appInfo: AppInfo) {
        viewModelScope.launch(coroutineContextProvider.backgroundContext) {
            try {
                appInfo.packageName = _appDetail.value?.packageName ?: ""
                insertAppInfoUseCase(appInfo)
                _appDetail.emit(
                    _appDetail.value?.copy(
                        description = appInfo.description,
                        category = appInfo.category
                    )
                )
            } catch (e: Throwable) {
                Log.e("app", e.localizedMessage ?: "")
            }
        }
    }

    fun updateDescription(appInfo: AppInfo?) {
        viewModelScope.launch(coroutineContextProvider.backgroundContext) {
            appInfo?.copy()?.let { _appInfo.emit(it) }
        }
    }
}
