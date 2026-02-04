package com.example.testtaskemploycity.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testtaskemploycity.R
import com.example.testtaskemploycity.presentation.ui.theme.AppColors


@Composable
fun CustomBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppColors.color_bg_default)
    ) {
        OutlineDivider()

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            BottomNavItem(
                isSelected = selectedTab == 0,
                label = stringResource(R.string.currencies),
                iconResId = R.drawable.ic_currencies,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(0.5f)
            )

            BottomNavItem(
                isSelected = selectedTab == 1,
                label = stringResource(R.string.favorites),
                iconResId = R.drawable.ic_favorites_on,
                onClick = { onTabSelected(1) },
                modifier = Modifier.weight(0.5f)
            )
        }

    }
}

@Composable
private fun BottomNavItem(
    isSelected: Boolean,
    label: String,
    iconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        if (isSelected) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = AppColors.color_main_light_primary)
            ) {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .width(64.dp)
                        .height(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = label,
                        tint = AppColors.color_main_primary
                    )
                }
            }
            Text(
                text = label,
                color = AppColors.color_main_text_default,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .width(64.dp)
                    .height(32.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = label,
                    tint = AppColors.color_main_secondary
                )
            }
            Text(
                text = label,
                color = AppColors.color_main_secondary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
