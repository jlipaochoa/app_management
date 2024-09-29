package com.example.app_management.extentions

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Environment
import android.os.StatFs

fun PackageManager.getLaunchApps(): List<ResolveInfo> {
    val intent = Intent(Intent.ACTION_MAIN, null)
    intent.addCategory(Intent.CATEGORY_LAUNCHER)
    return this.queryIntentActivities(intent, 0)
}

fun ResolveInfo.toAppInfoModel(packageManager: PackageManager): AppInfoModel {
    return AppInfoModel(
        this.loadLabel(packageManager).toString(),
        this.loadIcon(packageManager)
    )
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

class AppInfoModel(
    val name: String,
    val image: Drawable,
)
