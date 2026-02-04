package com.example.testtaskemploycity.presentation.utils

import java.util.Locale

fun Double.formatRate(): String = String.format(Locale.getDefault(), "%.6f", this)
