package br.com.fiap.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentInput: Double = 0.0
    private var previousInput: Double = 0.0
    private var operation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Configuração dos listeners dos botões
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        val numberButtons = listOf<Button>(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9
        )

        // Configuração dos listeners para os botões de números
        numberButtons.forEach { button ->
            button.setOnClickListener {
                val currentText = binding.textViewVisor.text.toString()
                val newText = if (currentText == "0") button.text.toString() else currentText + button.text.toString()
                binding.textViewVisor.text = newText
                currentInput = newText.toDouble()
            }
        }

        // Configuração dos listeners para os botões de operação
        binding.buttonAdd.setOnClickListener { handleOperation("+") }
        binding.buttonSubtract.setOnClickListener { handleOperation("-") }
        binding.buttonMultiply.setOnClickListener { handleOperation("*") }
        binding.buttonDivide.setOnClickListener { handleOperation("/") }
        binding.buttonPercent.setOnClickListener { handleOperation("%") }

        // Configuração do listener para o botão de inversão de sinal
        binding.buttonInvert.setOnClickListener {
            currentInput *= -1
            binding.textViewVisor.text = currentInput.toString()
        }

        // Configuração do listener para o botão de apagar valores
        binding.buttonClear.setOnClickListener {
            currentInput = 0.0
            binding.textViewVisor.text = "0"
        }

        // Configuração do listener para o botão de igual
        binding.buttonEquals.setOnClickListener { calculateResult() }
    }

    private fun handleOperation(op: String) {
        operation = op
        previousInput = currentInput
        if (operation != "%") {
            binding.textViewVisor.text = "0"
        }
    }

    private fun calculateResult() {
        when (operation) {
            "+" -> currentInput = previousInput + currentInput
            "-" -> currentInput = previousInput - currentInput
            "*" -> currentInput = previousInput * currentInput
            "/" -> {
                if (currentInput != 0.0) {
                    currentInput = previousInput / currentInput
                } else {
                    Toast.makeText(this, "Erro: Divisão por zero não permitida", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            "%" -> currentInput = previousInput * (currentInput / 100)
        }
        binding.textViewVisor.text = currentInput.toString()
        operation = ""
    }
}
