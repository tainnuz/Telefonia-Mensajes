package com.example.telefonia

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class PhoneStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                val savedNumber = SharedPrefManager.getSavedNumber(context)
                val autoReplyMessage = SharedPrefManager.getSavedMessage(context)

                if (incomingNumber == savedNumber) {
                    sendSMS(incomingNumber, autoReplyMessage)
                }
            }
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("PhoneStateReceiver", "SMS enviado a $phoneNumber")
        } catch (e: Exception) {
            Log.e("PhoneStateReceiver", "Error enviando SMS: ${e.message}")
        }
    }
}
