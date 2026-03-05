package com.lucasdev.jornadacerta.common.data.local.datasource

import com.lucasdev.jornadacerta.common.data.local.room.model.RegisterEntity

interface LocalDataSource {

    suspend fun getAllRegisters(): List<RegisterEntity>

    suspend fun getRegisterByDate(date: String): RegisterEntity?

    suspend fun getRecentRegisters():List<RegisterEntity>

    suspend fun insertOrUpdateRegister(registerEntity: RegisterEntity)

    suspend fun insertAll(list: List<RegisterEntity>)

    suspend fun deleteRegister(registerEntity: RegisterEntity)

    suspend fun deleteAllRegisters()

}