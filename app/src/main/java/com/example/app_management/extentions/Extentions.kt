package com.example.app_management.extentions

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs

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
