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
            // Obtiene el estado de la llamada
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            // Obtiene el numero entrante si esta disponible
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            // Verifica si el telefono esta sonando y si hay un numero entrante
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                if (incomingNumber != null) {
                    val savedNumber = SharedPrefManager.getSavedNumber(context)
                    val autoReplyMessage = SharedPrefManager.getSavedMessage(context)

                    Log.d("PhoneStateReceiver", "Numero guardado para respuesta automatica: $savedNumber")

                    // Si el numero entrante es el mismo que el guardado, envia el mensaje automatico
                    if (incomingNumber == savedNumber) {
                        sendSMS(incomingNumber, autoReplyMessage)
                        Log.d("PhoneStateReceiver", "Enviando SMS a $incomingNumber")
                    } else {
                        Log.d("PhoneStateReceiver", "El numero no coincide con el guardado")
                    }
                } else {
                    Log.d("PhoneStateReceiver", "El nimero entrante es null")
                }
            }
        }
    }

    // Metodo para enviar un SMS al numero especificado con el mensaje dado
    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            // Obtiene el SmsManager para enviar el mensaje
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("PhoneStateReceiver", "SMS enviado a $phoneNumber")
        } catch (e: Exception) {
            Log.e("PhoneStateReceiver", "Error enviando SMS: ${e.message}")
        }
    }
}
