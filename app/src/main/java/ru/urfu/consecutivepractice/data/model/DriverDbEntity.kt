package ru.urfu.consecutivepractice.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DriverDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "driverName") val name: String?,
    @ColumnInfo(name = "driverTag") val tag: String?,
    @ColumnInfo(name = "driverNationality") val nationality: String?
)