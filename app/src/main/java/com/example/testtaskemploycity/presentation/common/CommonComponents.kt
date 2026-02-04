package com.example.testtaskemploycity.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testtaskemploycity.presentation.ui.theme.AppColors


@Composable
fun TopBarTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        color = AppColors.color_main_text_default,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
fun OutlineDivider(modifier: Modifier = Modifier) {
    androidx.compose.material3.HorizontalDivider(
        color = AppColors.color_outline,
        modifier = modifier.fillMaxWidth()
    )
}


