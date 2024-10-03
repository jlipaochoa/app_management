import android.app.Application
import com.example.app_management.MainCoroutineRule
import com.example.domain.models.AppInfoAnalysisState
import com.example.domain.useCases.GetAppsUseCase
import com.example.app_management.presentation.apps.CoroutineContextProvider
import com.example.app_management.presentation.apps.HomeViewModel
import com.example.app_management.presentation.apps.components.StateWidgetAppBar
import com.example.app_management.presentation.ui.PermissionChecker
import com.example.domain.useCases.TypeAnalysis
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var viewModel: HomeViewModel
    private lateinit var getAppsUseCase: GetAppsUseCase
    private lateinit var permissionChecker: PermissionChecker

    private val coroutine =
        CoroutineContextProvider(StandardTestDispatcher(), UnconfinedTestDispatcher())

    @Before
    fun setup() {
        getAppsUseCase = mock(GetAppsUseCase::class.java)
        permissionChecker = mock(PermissionChecker::class.java)

        viewModel = HomeViewModel(
            getAppsUseCase = getAppsUseCase,
            application = mock(Application::class.java),
            permissionChecker = permissionChecker,
            coroutineContextProvider = coroutine
        )
    }

    @Test
    fun `getLaunchApps loads apps and updates state correctly`() = runTest {
        val apps = listOf(
            AppInfoAnalysisState("App 1", mock(), 500.0, 10.0, 20.0, "com.example.app1"),
            AppInfoAnalysisState("App 2", mock(), 1000.0, 20.0, 30.0, "com.example.app2")
        )

        `when`(getAppsUseCase(TypeAnalysis.Memory)).thenReturn(apps)

        viewModel.getLaunchApps()


        assertEquals(false, viewModel.loadingEvent.first())
    }

    @Test
    fun `orderByTypeAnalysis sorts apps correctly when permission granted`() = runTest {
        `when`(permissionChecker.hasUsageStatsPermission(anyOrNull())).thenReturn(true)

        val apps = listOf(
            AppInfoAnalysisState("App 1", mock(), 500.0, 10.0, 20.0, "com.example.app1"),
            AppInfoAnalysisState("App 2", mock(), 1000.0, 20.0, 30.0, "com.example.app2")
        )
        `when`(getAppsUseCase(TypeAnalysis.Memory)).thenReturn(apps)

        viewModel.getLaunchApps()
        advanceUntilIdle()
        viewModel.orderByTypeAnalysis(TypeAnalysis.Memory)
        advanceUntilIdle()
        val filteredApps = viewModel.filteredItems.first()
        assertEquals(apps.sortedByDescending { it.size }, filteredApps)
    }


    @Test
    fun `orderByTypeAnalysis requests permission if not granted`() = runTest {
        `when`(permissionChecker.hasUsageStatsPermission(anyOrNull())).thenReturn(false)

        viewModel.orderByTypeAnalysis(TypeAnalysis.Memory)

        verify(permissionChecker).requestUsageStatsPermission(anyOrNull())
    }

    @Test
    fun `setAppBarState updates the appBarState`() = runTest {
        assertEquals(StateWidgetAppBar.DEFAULT, viewModel.appBarState.first())

        viewModel.setAppBarState(StateWidgetAppBar.SEARCH)

        assertEquals(StateWidgetAppBar.SEARCH, viewModel.appBarState.first())
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
    fun `onSearchQueryChanged filters apps by query`() = runTest {
        `when`(permissionChecker.hasUsageStatsPermission(anyOrNull())).thenReturn(true)

        val apps = listOf(
            AppInfoAnalysisState("App 1", mock(), 500.0, 10.0, 20.0, "com.example.app1"),
            AppInfoAnalysisState("App 2", mock(), 1000.0, 20.0, 30.0, "com.example.app2")
        )
        `when`(getAppsUseCase(TypeAnalysis.Memory)).thenReturn(apps)

        viewModel.getLaunchApps()

        advanceUntilIdle()

        viewModel.onSearchQueryChanged("App 1")

        advanceUntilIdle()

        assertEquals(viewModel.filteredItems.value.firstOrNull()?.name, "App 1")
    }
}

