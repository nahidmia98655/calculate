package com.example.calculate.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    // Observe the state from the ViewModel
    val state = viewModel.state
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = if (state.expression.isEmpty()) "0" else state.expression,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = state.result,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            // First row: Clear, Backspace (icon), Divide, Multiply
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    label = "C",
                    backgroundColor = Color(0xFFEF5350)
                ) { viewModel.onAction(CalculatorAction.Clear) }
                IconButton(
                    onClick = { viewModel.onAction(CalculatorAction.Delete) },
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .background(Color(0xFFEF5350), shape = RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Backspace,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
                CalculatorButton(
                    label = "÷",
                    backgroundColor = Color(0xFF7C3AED)
                ) { viewModel.onAction(CalculatorAction.Operator("÷")) }
                CalculatorButton(
                    label = "×",
                    backgroundColor = Color(0xFF7C3AED)
                ) { viewModel.onAction(CalculatorAction.Operator("×")) }
            }
            // Number rows
            CalculatorButtonRow(
                buttons = listOf(
                    CalculatorButton("7") { viewModel.onAction(CalculatorAction.Number("7")) },
                    CalculatorButton("8") { viewModel.onAction(CalculatorAction.Number("8")) },
                    CalculatorButton("9") { viewModel.onAction(CalculatorAction.Number("9")) },
                    CalculatorButton("-", Color(0xFF7C3AED)) { viewModel.onAction(CalculatorAction.Operator("-")) }
                )
            )
            CalculatorButtonRow(
                buttons = listOf(
                    CalculatorButton("4") { viewModel.onAction(CalculatorAction.Number("4")) },
                    CalculatorButton("5") { viewModel.onAction(CalculatorAction.Number("5")) },
                    CalculatorButton("6") { viewModel.onAction(CalculatorAction.Number("6")) },
                    CalculatorButton("+", Color(0xFF7C3AED)) { viewModel.onAction(CalculatorAction.Operator("+")) }
                )
            )
            CalculatorButtonRow(
                buttons = listOf(
                    CalculatorButton("1") { viewModel.onAction(CalculatorAction.Number("1")) },
                    CalculatorButton("2") { viewModel.onAction(CalculatorAction.Number("2")) },
                    CalculatorButton("3") { viewModel.onAction(CalculatorAction.Number("3")) },
                    CalculatorButton("=", Color(0xFF7C3AED), weight = 1f) { viewModel.onAction(CalculatorAction.Equals) }
                )
            )
            CalculatorButtonRow(
                buttons = listOf(
                    CalculatorButton("0", weight = 2f) { viewModel.onAction(CalculatorAction.Number("0")) },
                    CalculatorButton(".") { viewModel.onAction(CalculatorAction.Number(".")) }
                )
            )
        }
    }
}

private data class CalculatorButton(
    val label: String,
    val backgroundColor: Color = Color(0xFFEEEEEE),
    val weight: Float = 1f,
    val onClick: () -> Unit
)

@Composable
private fun CalculatorButtonRow(buttons: List<CalculatorButton>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { btn ->
            Button(
                onClick = btn.onClick,
                colors = ButtonDefaults.buttonColors(containerColor = btn.backgroundColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(btn.weight)
                    .height(64.dp)
            ) {
                Text(
                    text = btn.label,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
