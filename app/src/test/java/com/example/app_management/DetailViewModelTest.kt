package com.example.app_management

import android.graphics.drawable.ColorDrawable
import com.example.domain.models.AppInfo
import com.example.domain.models.AppInfoDetailState
import com.example.domain.useCases.GetAppByPackageNameUseCase
import com.example.app_management.domain.useCases.InsertAppInfoUseCase
import com.example.app_management.presentation.apps.CoroutineContextProvider
import com.example.app_management.presentation.detailApp.DetailViewModel
import com.example.app_management.presentation.ui.theme.Red80
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var viewModel: DetailViewModel
    private lateinit var getAppByPackageNameUseCase: GetAppByPackageNameUseCase
    private lateinit var insertAppInfoUseCase: InsertAppInfoUseCase

    private val coroutine = CoroutineContextProvider(StandardTestDispatcher(),UnconfinedTestDispatcher())

    @Before
    fun setup() {
        getAppByPackageNameUseCase = mock(GetAppByPackageNameUseCase::class.java)
        insertAppInfoUseCase = mock(InsertAppInfoUseCase::class.java)

        viewModel = DetailViewModel(
            savedStateHandle = mock(),
            getDetailUseCase = getAppByPackageNameUseCase,
            insertAppInfoUseCase = insertAppInfoUseCase,
            coroutineContextProvider = coroutine
        )
    }

    @Test
    fun `getAppByPackageName loads app detail and analyses usage and permissions`() = runTest {
        val appDetail = AppInfoDetailState(
            name = "Test App",
            packageName = "com.example.test",
            isSystemApp = false,
            percentageUsage = 0.0,
            securityPercentages = 60.0,
            permissions = listOf(
                Pair("android.permission.CAMERA", true),
                Pair("android.permission.ACCESS_FINE_LOCATION", false)
            ),
            category = "Productivity",
            firstInstallTime = "2023-01-01",
            lastUpdateTime = "2023-09-01",
            sizeApp = 15.0,
            image = ColorDrawable()
        )
        `when`(getAppByPackageNameUseCase.invoke(anyString())).thenReturn(appDetail)

        viewModel.getAppByPackageName("com.example.test")

        advanceUntilIdle()

        assertEquals(appDetail, viewModel.appDetail.first())

        val expectedPermissionsAnalysis = Pair(
            "Te sugerimos que verifiques los permisos que le has otorgado a la app, ya que contiene permisos sensibles.",
            Red80
        )
        assertEquals(expectedPermissionsAnalysis, viewModel.analysisPermissions.first())

        val expectedUsageAnalysis = Pair(
            "Te sugerimos que lo borres, no lo usas.",
            Red80
        )
        assertEquals(expectedUsageAnalysis, viewModel.analysisUsage.first())
    }

    @Test
    fun `insertAppInfo updates app detail`() = runTest {
        val appDetail = AppInfoDetailState(
            name = "Test App",
            packageName = "com.example.test",
            isSystemApp = false,
            percentageUsage = 0.0,
            securityPercentages = 60.0,
            permissions = listOf(
                Pair("android.permission.CAMERA", true),
                Pair("android.permission.ACCESS_FINE_LOCATION", false)
            ),
            category = "Productivity",
            firstInstallTime = "2023-01-01",
            lastUpdateTime = "2023-09-01",
            sizeApp = 15.0,
            image = ColorDrawable()
        )
        `when`(getAppByPackageNameUseCase.invoke(anyString())).thenReturn(appDetail)
        viewModel.getAppByPackageName("com.example.test")

        val updatedAppInfo = AppInfo(
            packageName = "com.example.test",
            description = "New Description",
            category = "New Category"
        )

        `when`(insertAppInfoUseCase.invoke(anyOrNull())).thenReturn(Unit)

        viewModel.insertAppInfo(updatedAppInfo)

        val expectedUpdatedDetail = appDetail.copy(
            description = "New Description",
            category = "New Category"
        )
        assertEquals(expectedUpdatedDetail, viewModel.appDetail.first())
    }

    @Test
    fun `updateShowDialog toggles showDialog state`() = runTest {
        assertEquals(false, viewModel.showDialog.first())

        viewModel.updateShowDialog()

        assertEquals(true, viewModel.showDialog.first())
        viewModel.updateShowDialog()

        assertEquals(false, viewModel.showDialog.first())
    }

    @Test
    fun `updateDescription updates app info`() = runTest {
        val appInfo = AppInfo(
            packageName = "Test App Updated",
            description = "Updated Description",
            category = "Updated Category"
        )

        viewModel.updateDescription(appInfo)


        assertEquals(appInfo, viewModel.appInfo.first())
    }
}