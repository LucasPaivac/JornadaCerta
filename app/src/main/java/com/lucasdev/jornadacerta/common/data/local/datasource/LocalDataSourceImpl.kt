package com.lucasdev.jornadacerta.common.data.local.datasource

import com.lucasdev.jornadacerta.common.data.local.room.TimeRegisterDao
import com.lucasdev.jornadacerta.common.data.local.room.model.RegisterEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: TimeRegisterDao
): LocalDataSource {

    override suspend fun getAllRegisters(): List<RegisterEntity> {
        return dao.getAllRegisters()
    }

    override suspend fun getRegisterByDate(date: String): RegisterEntity? {
        return dao.getRegisterByDate(date)
    }

    override suspend fun getRecentRegisters(): List<RegisterEntity> {
        return dao.getRecentRegisters()
    }

    override suspend fun insertOrUpdateRegister(registerEntity: RegisterEntity) {
        return dao.insertOrUpdateRegister(registerEntity)
    }

    override suspend fun insertAll(list: List<RegisterEntity>) {
        return dao.insertAll(list)
    }


    override suspend fun deleteRegister(registerEntity: RegisterEntity) {
        return dao.deleteRegister(registerEntity)
    }

    override suspend fun deleteAllRegisters() {
        return dao.deleteAllRegisters()
    }

}