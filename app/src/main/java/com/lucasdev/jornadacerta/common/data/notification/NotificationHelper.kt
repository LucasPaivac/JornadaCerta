package com.lucasdev.jornadacerta.common.data.notification

interface NotificationHelper {

    fun showNotification(message: String, id: Int)
    fun scheduleJornadaReminders(estimatedTime: String, alreadyExited: Boolean)
    fun cancelAllReminders()

}