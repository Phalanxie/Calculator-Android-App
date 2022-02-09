package learnprogramming.academy


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import learnprogramming.academy.databinding.ActivityMainBinding
private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding


    private var operand1: Double? = null

    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(/*R.layout.activity_main*/view)



        val listener = View.OnClickListener { v ->
            val b = v as Button

            if (b == binding.buttonDot)
            {
                if (binding.newNumber.text.contains(".", true))
                {
                    binding.buttonDot.isEnabled = false
                } else
                {
                    binding.newNumber.append(b.text)
                    binding.buttonDot.isEnabled = true

                }
            }
            else
            {
                binding.newNumber.append(b.text)
                binding.buttonDot.isEnabled = true
            }
        }

        binding.button0.setOnClickListener(listener)
        binding.button1.setOnClickListener(listener)
        binding.button2.setOnClickListener(listener)
        binding.button3.setOnClickListener(listener)
        binding.button4.setOnClickListener(listener)
        binding.button5.setOnClickListener(listener)
        binding.button6.setOnClickListener(listener)
        binding.button7.setOnClickListener(listener)
        binding.button8.setOnClickListener(listener)
        binding.button9.setOnClickListener(listener)
        binding.buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try
            {

                val value = binding.newNumber.text.toString().toDouble()
                performOperation(value, op)
            }
            catch (e: NumberFormatException)
            {
                binding.newNumber.setText("")
            }

            pendingOperation = op
            binding.operation.text = pendingOperation
        }

        binding.buttonNeg?.setOnClickListener { view ->
            val value = binding.newNumber.text.toString()
            if (value.isEmpty())
            {
                binding.newNumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    binding.newNumber.setText(doubleValue.toString())
                } catch (e: NumberFormatException) {
                    binding.newNumber.setText("")
                }
            }
        }

        binding.buttonEquals.setOnClickListener(opListener)
        binding.buttonDivide.setOnClickListener(opListener)
        binding.buttonMultiply.setOnClickListener(opListener)
        binding.buttonMinus.setOnClickListener(opListener)
        binding.buttonPlus.setOnClickListener(opListener)


    }

    private fun performOperation(value: Double, operation: String)
    {
        if (operand1 == null) {
            operand1 = value
        } else {
             if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value

                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN//handle attempt to divide by zero
                } else {
                    operand1!! / value
                }

                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        binding.result.setText(operand1.toString())
        binding.newNumber.setText("")
    }


    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        if (operand1 != null)
        {
            outState.putDouble(STATE_OPERAND, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false)) {
            savedInstanceState.getDouble(STATE_OPERAND)

        } else {
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION).toString()
        binding.operation.text = pendingOperation

    }
}




