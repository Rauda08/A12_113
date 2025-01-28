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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically), // Memberi jarak antara gambar dan tombol
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Gambar logo yang lebih besar dan lebih ke atas
        Image(
            painter = painterResource(id = R.drawable.logomenu),
            contentDescription = "Logo Lembaga Kursus",
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 30.dp)
        )

        // Header teks
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),  // Menambahkan padding horizontal agar tombol tidak terlalu rapat
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center, // Menempatkan tombol di tengah
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onSiswaClick,
                    modifier = Modifier.size(120.dp), // Membuat tombol lebih besar
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C)),
                    shape = RectangleShape // Membuat tombol dengan sudut tajam
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, // Ikon untuk menu siswa
                        contentDescription = "Icon Siswa",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))  // Memberikan ruang antara tombol

                Button(
                    onClick = onInstrukturClick,
                    modifier = Modifier.size(120.dp), // Membuat tombol lebih besar
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C)),
                    shape = RectangleShape // Membuat tombol dengan sudut tajam
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle, // Ikon untuk menu instruktur
                        contentDescription = "Icon Instruktur",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))  // Memberikan ruang antara baris tombol

            Row(
                horizontalArrangement = Arrangement.Center, // Menempatkan tombol di tengah
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onKursusClick,
                    modifier = Modifier.size(120.dp), // Membuat tombol lebih besar
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C)),
                    shape = RectangleShape // Membuat tombol dengan sudut tajam
                ) {
                    Icon(
                        imageVector = Icons.Default.Star, // Ikon untuk menu kursus
                        contentDescription = "Icon Kursus",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))  // Memberikan ruang antara tombol

                Button(
                    onClick = onPendaftaranClick,
                    modifier = Modifier.size(120.dp), // Membuat tombol lebih besar
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46051C)),
                    shape = RectangleShape // Membuat tombol dengan sudut tajam
                ) {
                    Icon(
                        imageVector = Icons.Default.Create, // Ikon untuk menu pendaftaran
                        contentDescription = "Icon Pendaftaran",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeMenuView() {
    HomeMenuView(
        onSiswaClick = {},
        onInstrukturClick = {},
        onKursusClick = {},
        onPendaftaranClick = {}
    )
}
