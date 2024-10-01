package com.example.app_management.extentions

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process.myUid
import android.os.StatFs
import android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS
import android.widget.Toast
import com.example.app_management.mappers.FORMAT_DOUBLE
import java.util.Calendar

fun PackageManager.getLaunchApps(): List<ApplicationInfo> {
    val intent = Intent(Intent.ACTION_MAIN, null)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    return this.getInstalledApplications(PackageManager.GET_META_DATA)
}

fun Context.getRamData(): Pair<Long, Long> {
    val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)

    val totalRam = memoryInfo.totalMem / (1024 * 1024)
    val availableRam = memoryInfo.availMem / (1024 * 1024)
    return Pair(totalRam, totalRam - availableRam)
}

fun getInternalStorageData(): Pair<Long, Long> {
    val internalStorage = StatFs(Environment.getDataDirectory().path)
    val internalTotal = internalStorage.totalBytes / (1024 * 1024)
    val internalFree = internalStorage.availableBytes / (1024 * 1024)
    return Pair(internalTotal, internalTotal - internalFree)
}

fun Double.twoDecimals(): Double {
    return String.format(FORMAT_DOUBLE, this).toDouble()
}

fun Context.hasUsageStatsPermission(): Boolean {
    val appOpsManager = this.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        appOpsManager.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            myUid(),
            this.packageName
        )
    } else {
        appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            myUid(),
            this.packageName
        )
    }
    return mode == AppOpsManager.MODE_ALLOWED
}

fun Context.requestUsageStatsPermission() {
    try {
        val intent = Intent(ACTION_USAGE_ACCESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Agregar la flag
        this.startActivity(intent)
        Toast.makeText(
            this,
            "Por favor habilitar permisos para esta aplicacion",
            Toast.LENGTH_LONG
        ).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(
            this,
            "Error al momento de abrir configuraciones",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.timesUsageStats(): Pair<Long, List<UsageStats>> {
    val usageStatsManager =
        this.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val calendar = Calendar.getInstance()
    val endTime = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, -30)
    val startTime = calendar.timeInMillis

    val usageStatsList: List<UsageStats> = usageStatsManager.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        startTime,
        endTime
    )

    val totalForegroundTime = usageStatsList.sumOf { it.totalTimeInForeground }
    return Pair(totalForegroundTime, usageStatsList)
}

fun List<UsageStats>.getAppUsagePercentages(
    packageName: String,
    totalForegroundTime: Long
): Double {
    val appForegroundTime = this.filter { it.packageName == packageName }.sumOf { it.totalTimeInForeground }
    val percentage = (appForegroundTime.toDouble() / totalForegroundTime.toDouble()) * 100
    return percentage.twoDecimals()
}

fun Context.getSecurityPercentages(packageName: String): Double {
    val packageManager = this.packageManager
    val sensitivePermissions = listOf(
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
    val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
    val requestedPermissions = packageInfo.requestedPermissions ?: return 0.0
    val sensitivePermissionInApp = requestedPermissions.filter { it in sensitivePermissions }
    return ((sensitivePermissionInApp.size.toDouble() / sensitivePermissions.size.toDouble()) * 100).twoDecimals()
}