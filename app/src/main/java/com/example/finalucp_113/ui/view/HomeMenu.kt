package com.example.finalucp_113.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "dashboard"
    override val titleRes = "Dashboard"
}


@Composable
fun HomeMenuView(
    onSiswaClick: () -> Unit,
    onInstrukturClick : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color(0xFDEAABEF))
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selamat Datang",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB75C8E), // Deep pink color
            modifier = Modifier.padding(top = 40.dp)
        )

        Text(
            text = "di aplikasi Lembaga Kursus ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB75C8E), // Deep pink color
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = onSiswaClick,
            modifier = Modifier
                .padding(50.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD05E99)) // Light pink for button
        ) {
            Text(
                text = "Menu Siswa",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Button(
            onClick = onInstrukturClick,
            modifier = Modifier
                .padding(50.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD05E99)) // Light pink for button
        ) {
            Text(
                text = "Menu Instruktur",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
