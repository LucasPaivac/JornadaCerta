package com.lucasdev.jornadacerta.common.data.local.datasource

import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterEntity
import com.lucasdev.jornadacerta.common.model.TimeRegister

interface LocalDataSource {

    suspend fun getAllRegisters(): List<TimeRegisterEntity>

    suspend fun getRegisterByDate(date: String): TimeRegisterEntity?

    suspend fun insertOrUpdateRegister(registerEntity: TimeRegisterEntity)

    suspend fun deleteRegister(registerEntity: TimeRegisterEntity)

    suspend fun deleteAllRegisters()

}