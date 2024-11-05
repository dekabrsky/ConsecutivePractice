package ru.urfu.consecutivepractice.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import ru.urfu.consecutivepractice.data.db.DriversDatabase

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

object DatabaseBuilder {
    private var INSTANCE: DriversDatabase? = null

    fun getInstance(context: Context): DriversDatabase {
        if (INSTANCE == null) {
            synchronized(DriversDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DriversDatabase::class.java,
            "f1-example"
        ).build()
}