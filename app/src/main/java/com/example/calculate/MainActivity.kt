package com.example.calculate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculate.ui.theme.CalculateTheme
import com.example.calculate.ui.calculator.CalculatorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculateApp()
        }
    }
}

@Composable
private fun CalculateApp() {
    CalculateTheme {
        Surface {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "calculator") {
                composable("calculator") {
                    CalculatorScreen()
                }
            }
        }
    }
}
