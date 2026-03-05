package com.lucasdev.jornadacerta.common.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucasdev.jornadacerta.common.data.local.room.model.RegisterEntity

@Dao
interface TimeRegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRegister(register: RegisterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(register: List<RegisterEntity>)

    @Query("Select * From time_register ORDER BY id DESC")
    suspend fun getAllRegisters():List<RegisterEntity>

    @Query("Select * From time_register where date is :date")
    suspend fun getRegisterByDate(date: String): RegisterEntity?

    @Query("SELECT * FROM time_register ORDER BY date DESC LIMIT 3")
    suspend fun getRecentRegisters(): List<RegisterEntity>

    @Delete
    suspend fun deleteRegister(register: RegisterEntity)

    @Query("DELETE FROM time_register")
    suspend fun deleteAllRegisters()

}