package com.example.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var textViewFirstNumber: EditText
    lateinit var textViewSecondNumber: EditText
    lateinit var textViewOperation: TextView
    lateinit var textViewAnswer: TextView
    lateinit var textViewError: TextView

    @SuppressLint("ResourceAsColor", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewFirstNumber = findViewById(R.id.label1)
        textViewFirstNumber.requestFocus()
        textViewSecondNumber = findViewById(R.id.label2)
        textViewOperation = findViewById(R.id.textViewOperation)
        textViewOperation.text = ""
        textViewAnswer = findViewById(R.id.textViewAnswer)
        textViewAnswer.text = ""
        textViewError = findViewById(R.id.textView)
        textViewError.setVisibility(View.INVISIBLE)
        var button1 = findViewById<Button>(R.id.button1)
        var button2 = findViewById<Button>(R.id.button2)
        var button3 = findViewById<Button>(R.id.button3)
        var button4 = findViewById<Button>(R.id.button9)
        var button5 = findViewById<Button>(R.id.button8)
        var button6 = findViewById<Button>(R.id.button7)
        var button7 = findViewById<Button>(R.id.button13)
        var button8 = findViewById<Button>(R.id.button12)
        var button9 = findViewById<Button>(R.id.button11)
        var button0 = findViewById<Button>(R.id.button16)
        var buttonPlus = findViewById<Button>(R.id.button15)
        var buttonMinus = findViewById<Button>(R.id.button6)
        var buttonMultiply = findViewById<Button>(R.id.button10)
        var buttonDivision = findViewById<Button>(R.id.button5)
        var buttonEqual = findViewById<Button>(R.id.button17)
        var buttonDot = findViewById<Button>(R.id.button4)
        var buttonAC = findViewById<Button>(R.id.buttonAC)

        button1.setOnClickListener{ handleNumberAndDotButtonAction("1") }
        button2.setOnClickListener{ handleNumberAndDotButtonAction("2") }
        button3.setOnClickListener{ handleNumberAndDotButtonAction("3") }
        button4.setOnClickListener{ handleNumberAndDotButtonAction("4") }
        button5.setOnClickListener{ handleNumberAndDotButtonAction("5") }
        button6.setOnClickListener{ handleNumberAndDotButtonAction("6") }
        button7.setOnClickListener{ handleNumberAndDotButtonAction("7") }
        button8.setOnClickListener{ handleNumberAndDotButtonAction("8") }
        button9.setOnClickListener{ handleNumberAndDotButtonAction("9") }
        button0.setOnClickListener{ handleNumberAndDotButtonAction("0") }
        buttonPlus.setOnClickListener { handleOperationButtonAction("+") }
        buttonMinus.setOnClickListener { handleOperationButtonAction("-") }
        buttonMultiply.setOnClickListener { handleOperationButtonAction("*") }
        buttonDivision.setOnClickListener { handleOperationButtonAction("/") }
        buttonEqual.setOnClickListener { handleButtonEqualAction() }
        buttonDot.setOnClickListener { handleNumberAndDotButtonAction(".") }
        buttonAC.setOnClickListener { handleButtonAcAction() }

    }

    private fun handleButtonAcAction() {
        textViewFirstNumber.text.clear()
        textViewSecondNumber.text.clear()
        textViewOperation.text = ""
        textViewAnswer.text = ""
        textViewFirstNumber.requestFocus()
        textViewError.visibility = View.INVISIBLE
    }

    private fun handleButtonEqualAction() {
        textViewSecondNumber.clearFocus()
        textViewError.setVisibility(View.INVISIBLE)
        var answer: Float = 0.0F
        if (textViewFirstNumber.text.isEmpty()) {
            textViewFirstNumber.error = "Введите число"
        }

        else if (textViewSecondNumber.text.isEmpty()) {
            textViewSecondNumber.error = "Введите число"
        }

        else if (textViewOperation.text.isEmpty()) {
            textViewOperation.error = "Введите знак операции"
        }

        else {
            var intAnswer: Int
            var firstNumber = textViewFirstNumber.text.toString().toFloat()
            var secondNumber = textViewSecondNumber.text.toString().toFloat()
            var operation = textViewOperation.text.toString()
            when (operation) {
                "+" -> answer = firstNumber + secondNumber
                "-" -> answer = firstNumber - secondNumber
                "*" -> answer = firstNumber * secondNumber
                "/" -> {
                    if (secondNumber == 0f) {
                        textViewAnswer.text = ""
                        textViewError.setVisibility(View.VISIBLE)
                        return
                    }
                    else
                        answer = firstNumber / secondNumber
                }
            }
            textViewFirstNumber.text.clear()
            textViewSecondNumber.text.clear()
            textViewOperation.text = ""
            textViewAnswer.text = ""
            if(answer.isInt()) {
                intAnswer = answer.toInt()
                textViewAnswer.text = intAnswer.toString()
            } else {
                textViewAnswer.text = answer.toString()
            }
        }
    }

    private fun Float.isInt(): Boolean {
        return this % 1 == 0f
    }


    private fun handleOperationButtonAction(operation: String) {
        textViewError.setVisibility(View.INVISIBLE)
        var answer: String

        if (!textViewFirstNumber.text.isEmpty()
            && textViewOperation.text.isEmpty()
            && textViewSecondNumber.text.isEmpty()
            && textViewAnswer.text.isEmpty()) {
            textViewOperation.text = operation
            textViewSecondNumber.requestFocus()
        }

        else if (!textViewFirstNumber.text.isEmpty()
            && !textViewOperation.text.isEmpty()
            && textViewSecondNumber.text.isEmpty()
            && textViewAnswer.text.isEmpty()) {
            textViewOperation.text = operation
            textViewSecondNumber.requestFocus()
        }

        else if (!textViewFirstNumber.text.isEmpty()
            && !textViewOperation.text.isEmpty()
            && !textViewSecondNumber.text.isEmpty()
            && textViewAnswer.text.isEmpty()) {
            handleButtonEqualAction()
            answer = textViewAnswer.text.toString()
            textViewAnswer.text = ""
            textViewFirstNumber.text.clear()
            textViewFirstNumber.text.append(answer)
            textViewOperation.text = operation
            textViewSecondNumber.text.clear()
            textViewSecondNumber.requestFocus()
        } else if (!textViewAnswer.text.isEmpty()) {
            answer = textViewAnswer.text.toString()
            textViewAnswer.text = ""
            textViewFirstNumber.text.clear()
            textViewFirstNumber.text.append(answer)
            textViewOperation.text = operation
            textViewSecondNumber.text.clear()
            textViewSecondNumber.requestFocus()
        }
    }

    private fun handleNumberAndDotButtonAction(symbol: String) {
        textViewError.setVisibility(View.INVISIBLE)
        var activeTextField = getActiveTextField()
        activeTextField?.append(symbol)
    }

    private fun getActiveTextField(): EditText? {
        return when {
            textViewFirstNumber.isFocused -> textViewFirstNumber
            textViewSecondNumber.isFocused -> textViewSecondNumber
            else -> null
        }
    }

}