import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
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
            .background(Color(0xFFEFB8C8)),
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logomenu),
            contentDescription = "Logo Lembaga Kursus",
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 30.dp)
        )

        // Selamat Datang
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

        // Tombol
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuButton(
                    label = "Siswa",
                    icon = Icons.Default.Person,
                    onClick = onSiswaClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                MenuButton(
                    label = "Instruktur",
                    icon = Icons.Default.AccountCircle,
                    onClick = onInstrukturClick
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuButton(
                    label = "Kursus",
                    icon = Icons.Default.Star,
                    onClick = onKursusClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                MenuButton(
                    label = "Pendaftaran",
                    icon = Icons.Default.Create,
                    onClick = onPendaftaranClick
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(140.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFDD5E3)), // Warna latar belakang
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(2.dp, Color(0xFF46051C))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(50.dp),
                tint = Color(0xFF46051C)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color(0xFF46051C),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
