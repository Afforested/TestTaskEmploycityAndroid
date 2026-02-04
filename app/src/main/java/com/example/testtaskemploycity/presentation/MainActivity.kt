package com.example.testtaskemploycity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.testtaskemploycity.presentation.navigation.NavGraph
import com.example.testtaskemploycity.presentation.ui.theme.TestTaskEmploycityTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTaskEmploycityTheme {
                NavGraph()
            }
        }
    }
}