package com.example.app_management.domain.models

import android.app.usage.StorageStatsManager
import android.app.usage.UsageStats
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.UserHandle
import android.os.storage.StorageManager
import com.example.app_management.extentions.getAppUsagePercentages
import com.example.app_management.extentions.getSecurityPercentages
import com.example.app_management.extentions.toDate
import com.example.app_management.extentions.twoDecimals
import com.example.app_management.util.sensitivePermissions

fun Context.getAppSize(packageName: String): Double {
    val storageStatsManager =
        this.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
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

fun ApplicationInfo.toAppInfoModel(
    packageManager: PackageManager,
    context: Context,
    times: Pair<Long, List<UsageStats>>
): AppInfoAnalysis {
    return AppInfoAnalysis(
        this.loadLabel(packageManager).toString(),
        this.loadIcon(packageManager),
        context.getAppSize(this.packageName),
        context.getSecurityPercentages(this.packageName),
        times.second.getAppUsagePercentages(this.packageName, times.first),
        this.packageName
    )
}

fun ApplicationInfo.toAppInfoDetail(
    packageManager: PackageManager,
    context: Context,
    times: Pair<Long, List<UsageStats>>
): AppInfoDetail {
    return AppInfoDetail(
        name = this.loadLabel(packageManager).toString(),
        packageName = this.packageName,
        image = this.loadIcon(packageManager),
        version = packageManager.getPackageInfo(packageName, 0).versionName,
        versionCode = packageManager.getPackageInfo(packageName, 0).versionCode,
        permissions = packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions.toList().map { Pair(it, it in sensitivePermissions) },
        firstInstallTime = packageManager.getPackageInfo(packageName, 0).firstInstallTime.toDate(),
        lastUpdateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime.toDate(),
        isSystemApp = (this.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
        sizeApp = context.getAppSize(this.packageName),
        securityPercentages = context.getSecurityPercentages(this.packageName),
        percentageUsage = times.second.getAppUsagePercentages(this.packageName, times.first),
    )
}
