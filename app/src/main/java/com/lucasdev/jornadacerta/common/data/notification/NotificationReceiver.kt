package com.lucasdev.jornadacerta.common.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver: BroadcastReceiver() {

    @Inject lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context, intent: Intent) {

        val message = intent.getStringExtra("MESSAGE") ?: return
        val notificationId = intent.getIntExtra("NOTIFICATION_ID", 1)

        notificationHelper.showNotification(message, notificationId)

    }

}