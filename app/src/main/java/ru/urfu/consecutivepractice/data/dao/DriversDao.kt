package ru.urfu.consecutivepractice.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.urfu.consecutivepractice.data.model.DriverDbEntity

@Dao
interface DriversDao {
    @Query("SELECT * FROM DriverDbEntity")
    suspend fun getAll(): List<DriverDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: DriverDbEntity)
}