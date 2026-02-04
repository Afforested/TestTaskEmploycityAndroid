package com.example.testtaskemploycity.presentation.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtaskemploycity.R
import com.example.testtaskemploycity.domain.model.SortType
import com.example.testtaskemploycity.presentation.common.OutlineDivider
import com.example.testtaskemploycity.presentation.ui.theme.AppColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    currentSortType: SortType,
    onSortTypeSelected: (SortType) -> Unit,
    onBackClick: () -> Unit
) {
    var selectedSort by remember { mutableStateOf(currentSortType) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.color_bg_default)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.color_bg_header)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 4.dp, end = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onBackClick() }
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = AppColors.color_main_primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.filters),
                    color = AppColors.color_main_text_default,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 48.dp)
                )
            }

            OutlineDivider()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.sort_by),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.color_main_text_secondary,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            val sortOptions = listOf(
                SortType.ALPHABETIC_ASC to stringResource(R.string.code_a_z),
                SortType.ALPHABETIC_DESC to stringResource(R.string.code_z_a),
                SortType.RATE_ASC to stringResource(R.string.quote_asc),
                SortType.RATE_DESC to stringResource(R.string.quote_desc)
            )

            sortOptions.forEach { (sortType, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedSort == sortType,
                            onClick = { selectedSort = sortType }
                        )
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.color_main_text_default
                    )
                    RadioButton(
                        selected = selectedSort == sortType,
                        onClick = { selectedSort = sortType },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppColors.color_main_primary,
                            unselectedColor = AppColors.color_main_secondary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onSortTypeSelected(selectedSort)
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.color_main_primary
                ),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        letterSpacing = 0.1.sp
                    ),
                    color = AppColors.color_main_on_primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}
