package com.example.calculate.ui.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> appendNumber(action.digit)
            is CalculatorAction.Operator -> appendOperator(action.operator)
            is CalculatorAction.Parenthesis -> appendParenthesis(action.parenthesis)
            CalculatorAction.Percent -> applyPercent()
            CalculatorAction.ToggleSign -> toggleSign()
            CalculatorAction.Clear -> clear()
            CalculatorAction.Delete -> deleteLast()
            CalculatorAction.Equals -> calculateResult()
        }
    }

    private fun appendNumber(digit: String) {
        if (state.result != "0" && state.expression.isEmpty()) {
            // Starting new calculation after previous result
            state = state.copy(result = "0")
        }
        state = state.copy(expression = state.expression + digit)
    }

    private fun appendOperator(operator: String) {
        if (state.expression.isNotEmpty() && !state.expression.last().isDigit()) {
            // Replace the previous operator
            state = state.copy(expression = state.expression.dropLast(1) + operator)
        } else if (state.expression.isNotEmpty()) {
            state = state.copy(expression = state.expression + operator)
        }
    }

    private fun appendParenthesis(parenthesis: String) {
        state = state.copy(expression = state.expression + parenthesis)
    }

    private fun applyPercent() {
        // Convert the current number to percent (divide by 100)
        val sanitized = state.expression
            .replace("÷", "/")
            .replace("×", "*")
        try {
            val expr = ExpressionBuilder(sanitized).build()
            val eval = expr.evaluate()
            val percentValue = eval / 100.0
            state = state.copy(result = percentValue.toString())
        } catch (e: Exception) {
            state = state.copy(result = "Error")
        }
    }

    private fun toggleSign() {
        if (state.expression.isNotEmpty()) {
            // Simple toggle: prepend '-' if not negative, else remove leading '-'
            state = if (state.expression.startsWith("-")) {
                state.copy(expression = state.expression.removePrefix("-"))
            } else {
                state.copy(expression = "-" + state.expression)
            }
        }
    }

    private fun clear() {
        state = CalculatorState()
    }

    private fun deleteLast() {
        if (state.expression.isNotEmpty()) {
            state = state.copy(expression = state.expression.dropLast(1))
        }
    }

    private fun calculateResult() {
        try {
            // Replace the unicode division and multiplication symbols if used
            val sanitized = state.expression
                .replace("÷", "/")
                .replace("×", "*")
            val expr = ExpressionBuilder(sanitized).build()
            val eval = expr.evaluate()
            val formatted = if (eval % 1.0 == 0.0) {
                eval.toLong().toString()
            } else {
                "%.4f".format(eval)
            }
            state = state.copy(result = formatted)
        } catch (e: Exception) {
            state = state.copy(result = "Error")
        }
    }
}

sealed class CalculatorAction {
    data class Number(val digit: String) : CalculatorAction()
    data class Operator(val operator: String) : CalculatorAction()
    data class Parenthesis(val parenthesis: String) : CalculatorAction()
    object Percent : CalculatorAction()
    object ToggleSign : CalculatorAction()
    object Clear : CalculatorAction()
    object Delete : CalculatorAction()
    object Equals : CalculatorAction()
}
