package com.example.app_management.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AppInfo")
data class AppInfo(
    @PrimaryKey
    val packageName: String,
    val description: String,
    val category: String
)