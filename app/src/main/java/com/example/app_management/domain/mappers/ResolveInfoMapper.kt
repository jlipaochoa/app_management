package com.example.app_management.domain.mappers

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.UserHandle
import android.os.storage.StorageManager
import com.example.app_management.domain.models.AppInfoModel
import com.example.app_management.extentions.getAppUsagePercentages
import com.example.app_management.extentions.getSecurityPercentages
import com.example.app_management.extentions.twoDecimals

const val FORMAT_DOUBLE = "%.2f"
fun Context.getAppSize(packageName: String): Double {
    val storageStatsManager =
        this.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
    val userHandle = UserHandle.getUserHandleForUid(android.os.Process.myUid())

    try {
        val storageStats = storageStatsManager.queryStatsForPackage(
            StorageManager.UUID_DEFAULT, packageName, userHandle
        )
        val appSizeMB =  (storageStats.appBytes.toDouble() / (1024 * 1024))
        return appSizeMB.twoDecimals()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return 0.0
}

fun ApplicationInfo.toAppInfoModel(
    packageManager: PackageManager,
    context: Context,
    times: Pair<Long, List<android.app.usage.UsageStats>>
): AppInfoModel {
    return AppInfoModel(
        this.loadLabel(packageManager).toString(),
        this.loadIcon(packageManager),
        context.getAppSize(this.packageName),
        context.getSecurityPercentages(this.packageName),
        times.second.getAppUsagePercentages(this.packageName, times.first),
        this.packageName
    )
}


