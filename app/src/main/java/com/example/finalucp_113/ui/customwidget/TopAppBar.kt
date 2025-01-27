package com.example.finalucp_113.ui.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeTopAppBar(
    title: String,
    canNavigateBack : Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior ?= null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
    iconColor: Color = Color(0xFF46051C)
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color(0xFF46051C),
                fontSize = 30.sp, // Ukuran font
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(y = -20.dp)// Membuat font bold
            )
        },
        modifier = modifier
            .offset(y = (0).dp)
            .fillMaxWidth(),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = iconColor, modifier = Modifier.offset(y = -10.dp))

                }
            }
        },
        actions = {
            IconButton(onClick = onRefresh) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh", tint = iconColor, modifier = Modifier.offset(y = -10.dp))
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFDD5E3)
        )
    )
}