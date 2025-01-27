package com.example.finalucp_113.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalucp_113.R
import com.example.finalucp_113.ui.navigation.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "dashboard"
    override val titleRes = "Dashboard"
}

@Composable
fun HomeMenuView(
    onSiswaClick: () -> Unit,
    onInstrukturClick: () -> Unit,
    onKursusClick: () -> Unit,
    onPendaftaranClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(Color(0xFFEFB8C8)),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.kursuss),
            contentDescription = "Logo Lembaga Kursus",
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(top = 30.dp)
        )

        Text(
            text = "Selamat Datang",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF46051C),
            modifier = Modifier.padding(top = 20.dp)
        )

        Text(
            text = "di aplikasi Lembaga Kursus",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF46051C),
            modifier = Modifier.padding(top = 10.dp)
        )

        Button(
            onClick = onSiswaClick,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C))
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Menu Siswa",
                modifier = Modifier.padding(end = 10.dp),
                tint = Color.White
            )
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
                .padding(20.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C))
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Menu Instruktur",
                modifier = Modifier.padding(end = 10.dp),
                tint = Color.White
            )
            Text(
                text = "Menu Instruktur",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Button(
            onClick = onKursusClick,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C))
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Menu Kursus",
                modifier = Modifier.padding(end = 10.dp),
                tint = Color.White
            )
            Text(
                text = "Menu Kursus",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Button(
            onClick = onPendaftaranClick,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C))
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Menu Pendaftaran",
                modifier = Modifier.padding(end = 10.dp),
                tint = Color.White
            )
            Text(
                text = "Menu Pendaftaran",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
