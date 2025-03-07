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

            Log.d("PhoneStateReceiver", "Estado de la llamada: $state")

            if (incomingNumber != null) {
                Log.d("PhoneStateReceiver", "Numero entrante: $incomingNumber")
            } else {
                Log.d("PhoneStateReceiver", "Numero entrante es null")
            }

            if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                val savedNumber = SharedPrefManager.getSavedNumber(context)
                val autoReplyMessage = SharedPrefManager.getSavedMessage(context)

                Log.d("PhoneStateReceiver", "Numero guardado para respuesta automática: $savedNumber")

                if (incomingNumber == savedNumber) {
                    sendSMS(incomingNumber, autoReplyMessage)
                    Log.d("PhoneStateReceiver", "Enviando SMS a $incomingNumber")
                } else {
                    Log.d("PhoneStateReceiver", "El numero no coincide con el guardado")
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
