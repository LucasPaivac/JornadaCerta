package com.lucasdev.jornadacerta.common.data.repository

import com.lucasdev.jornadacerta.common.data.local.datasource.LocalDataSource
import com.lucasdev.jornadacerta.common.model.Register
import com.lucasdev.jornadacerta.common.utils.toDomain
import com.lucasdev.jornadacerta.common.utils.toEntity
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val local: LocalDataSource
) {

    //Register - Screen
    suspend fun getRegisterByDate(date: String): Result<Register?>{

        return try {

            val registerEntity = local.getRegisterByDate(date = date)
            return Result.success(registerEntity?.toDomain())

        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun insertOrUpdateRegister(register: Register){

        local.insertOrUpdateRegister(register.toEntity())

    }

    /*suspend fun getAllRegisters(): List<TimeRegister>{

    }

    suspend fun deleteRegister(register: TimeRegister){

    }

    suspend fun deleteAllRegisters(){

    }*/

}