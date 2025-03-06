package com.example.telefonia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoReplyScreen()
        }
    }
}

@Composable
fun AutoReplyScreen() {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    var message by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "CONTESTAR",
            fontSize = 24.sp,
            color = Color(0xFF388E3C) // Verde oscuro
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de número
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Número de teléfono") },
            modifier = Modifier.fillMaxWidth(0.8f),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4CAF50), // Verde
                unfocusedBorderColor = Color(0xFF8BC34A) // Verde claro
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de mensaje
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mensaje de respuesta") },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color(0xFF8BC34A)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón alineado a la derecha
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    SharedPrefManager.saveNumber(context, phoneNumber.text)
                    SharedPrefManager.saveMessage(context, message.text)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)) // Verde oscuro
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Guardar")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar")
            }
        }
    }
}
