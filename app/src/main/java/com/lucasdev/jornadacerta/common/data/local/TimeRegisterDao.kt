package com.lucasdev.jornadacerta.common.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimeRegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRegister(timeRegister: TimeRegisterEntity)

    @Query("Select * From time_register ORDER BY id DESC")
    suspend fun getAllRegisters():List<TimeRegisterEntity>

    @Query("Select * From time_register where date is :date")
    suspend fun getRegisterByDate(date: String): TimeRegisterEntity?

    @Delete
    suspend fun deleteRegister(timeRegister: TimeRegisterEntity)

    @Query("DELETE FROM time_register")
    suspend fun deleteAllRegisters()

}