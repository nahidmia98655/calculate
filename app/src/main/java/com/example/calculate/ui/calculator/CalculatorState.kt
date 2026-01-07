package com.example.calculate.ui.calculator

import androidx.compose.runtime.Immutable

@Immutable
data class CalculatorState(
    val expression: String = "",
    val result: String = "0"
)
