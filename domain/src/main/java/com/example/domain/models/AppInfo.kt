package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AppInfo")
data class AppInfo(
    @PrimaryKey
    var packageName: String,
    var description: String,
    var category: String
)