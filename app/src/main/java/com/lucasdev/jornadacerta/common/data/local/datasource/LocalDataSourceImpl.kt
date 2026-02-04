package com.lucasdev.jornadacerta.common.data.local.datasource

import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDao
import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterEntity
import com.lucasdev.jornadacerta.common.model.TimeRegister

class LocalDataSourceImpl(
    private val dao: TimeRegisterDao
): LocalDataSource {

    override suspend fun getAllRegisters(): List<TimeRegisterEntity> {
        return dao.getAllRegisters()
    }

    override suspend fun getRegisterByDate(date: String): TimeRegisterEntity? {
        return dao.getRegisterByDate(date)
    }

    override suspend fun insertOrUpdateRegister(registerEntity: TimeRegisterEntity) {
        return dao.insertOrUpdateRegister(registerEntity)
    }

    override suspend fun deleteRegister(registerEntity: TimeRegisterEntity) {
        return dao.deleteRegister(registerEntity)
    }

    override suspend fun deleteAllRegisters() {
        return dao.deleteAllRegisters()
    }

}