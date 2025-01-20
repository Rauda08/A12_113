package com.example.finalucp_113.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun HomeMenuViewPreview() {
    HomeMenuView(
        onDosenClick = { },
        onMatkulClick = { }
    )
}

@Composable
fun HomeMenuView(
    onDosenClick: () -> Unit,
    onMatkulClick: () -> Unit
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
        // Welcome Text
        Text(
            text = "Selamat Datang",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB75C8E), // Deep pink color
            modifier = Modifier.padding(top = 25.dp)
        )

        Text(
            text = "di aplikasi Lembaga Kursus ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB75C8E), // Deep pink color
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}