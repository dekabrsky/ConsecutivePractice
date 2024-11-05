package ru.urfu.consecutivepractice.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.urfu.consecutivepractice.data.dao.DriversDao
import ru.urfu.consecutivepractice.data.model.DriverDbEntity

@Database(entities = [DriverDbEntity::class], version = 1)
abstract class DriversDatabase : RoomDatabase() {
    abstract fun driversDao(): DriversDao
}