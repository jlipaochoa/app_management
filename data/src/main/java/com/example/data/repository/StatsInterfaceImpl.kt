package com.example.data.repository

import android.app.Application
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.UserHandle
import android.os.storage.StorageManager
import com.example.domain.models.AppInfoAnalysis
import com.example.domain.repository.StatsInterface
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class StatsInterfaceImpl @Inject constructor(private val application: Application) :
    StatsInterface {
    private val sensitivePermissions = listOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.CAMERA",
        "android.permission.RECORD_AUDIO",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_CONTACTS",
        "android.permission.WRITE_CONTACTS",
        "android.permission.GET_ACCOUNTS",
        "android.permission.READ_PHONE_STATE",
        "android.permission.CALL_PHONE",
        "android.permission.READ_CALL_LOG",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.SEND_SMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_MMS",
        "android.permission.BODY_SENSORS",
        "android.permission.ACCESS_BACKGROUND_LOCATION",
        "android.permission.POST_NOTIFICATIONS"
    )

    override fun getLaunchAppsWithStats(): List<AppInfoAnalysis> {
        val usageStatsList = getUsageStatsList()
        val totalForegroundTime = usageStatsList.sumOf { it.totalTimeInForeground }
        val installedApps = getInstalledApps()

        return installedApps.map { app ->
            mapAppInfoToAnalysis(app, usageStatsList, totalForegroundTime)
        }
    }

    private fun getUsageStatsList(): List<UsageStats> {
        val usageStatsManager =
            application.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, -30)
        val startTime = calendar.timeInMillis

        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )
    }

    private fun getInstalledApps(): List<ApplicationInfo> {
        return application.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    }

    private fun mapAppInfoToAnalysis(
        app: ApplicationInfo,
        usageStatsList: List<UsageStats>,
        totalForegroundTime: Long
    ): AppInfoAnalysis {
        val percentage = usageStatsList.filter { it.packageName == app.packageName }
            .sumOf { it.totalTimeInForeground }

        val permissionSensitive =
            getPermissionApps(app.packageName).map { Pair(it, it in sensitivePermissions) }
        val totalSensiblePermission = permissionSensitive.filter { it.second }

        return AppInfoAnalysis(
            name = app.loadLabel(application.packageManager).toString(),
            image = app.loadIcon(application.packageManager),
            size = getAppSize(app.packageName),
            percentageRisk = (totalSensiblePermission.size.toDouble() / sensitivePermissions.size.toDouble() * 100).twoDecimals(),
            percentageUsage = (percentage.toDouble() / totalForegroundTime.toDouble() * 100),
            packageName = app.packageName,
            systemApp = (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
            permissions = permissionSensitive,
            firstInstallTime = application.packageManager.getPackageInfo(
                app.packageName,
                0
            ).firstInstallTime.toDate(),
            lastUpdateTime = application.packageManager.getPackageInfo(
                app.packageName,
                0
            ).lastUpdateTime.toDate(),
            version = application.packageManager.getPackageInfo(app.packageName, 0).versionName,
            versionCode = application.packageManager.getPackageInfo(app.packageName, 0).versionCode,
        )
    }

    override fun getAppWithStats(packageName: String): AppInfoAnalysis? {
        val usageStatsList = getUsageStatsList()
        val totalForegroundTime = usageStatsList.sumOf { it.totalTimeInForeground }
        val app = getInstalledApps().find { it.packageName == packageName } ?: return null

        return mapAppInfoToAnalysis(app, usageStatsList, totalForegroundTime)
    }

    override fun getAppSize(packageName: String): Double {
        val storageStatsManager =
            application.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
        val userHandle = UserHandle.getUserHandleForUid(android.os.Process.myUid())

        try {
            val storageStats = storageStatsManager.queryStatsForPackage(
                StorageManager.UUID_DEFAULT, packageName, userHandle
            )
            val appSizeMB = (storageStats.appBytes.toDouble() / (1024 * 1024))
            return appSizeMB.twoDecimals()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0.0
    }

    override fun getPermissionApps(packageName: String): List<String> {
        val packageInfo =
            application.packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        return packageInfo.requestedPermissions?.toList() ?: emptyList()
    }

}

fun Double.twoDecimals(): Double {
    return String.format(FORMAT_DOUBLE, this).toDouble()
}

const val FORMAT_DOUBLE = "%.2f"


fun Long.toDate(): String {
    try {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(this)
        return dateFormat.format(date)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    return "Fecha no disponible"
}