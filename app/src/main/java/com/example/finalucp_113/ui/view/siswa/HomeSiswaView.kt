package com.example.finalucp_113.ui.view.siswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalucp_113.model.Siswa






@Composable
fun SiswaCard(
    siswa: Siswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Siswa) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.nama_siswa,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(siswa) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Siswa"
                    )
                }
            }
            Text(text = "ID: ${siswa.id_siswa}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Email: ${siswa.email}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "No Telepon: ${siswa.no_telepon}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
