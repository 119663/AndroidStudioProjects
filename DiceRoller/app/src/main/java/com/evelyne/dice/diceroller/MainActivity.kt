package com.evelyne.dice.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //declare and initialize variables

        val buttonToast: Button = findViewById(R.id.button_toast)
        buttonToast.setOnClickListener { showToast()  }

        val buttonRoll: Button = findViewById(R.id.button_roll)
        buttonRoll.setOnClickListener { rollDice()  }

        val buttonCount: Button = findViewById(R.id.button_count)
        buttonCount.setOnClickListener { rollCount() }
    }
    //create method for toss
    private fun showToast(){
        Toast.makeText(this, "Ooh My, you have tossed me", Toast.LENGTH_SHORT).show()
    }
    private fun rollDice(){
        val generatedRandom = (1..6).random()
        val textViewResult: TextView = findViewById(R.id.text_result)
        textViewResult.text = generatedRandom.toString()
    }
    private fun rollCount(){
        val textViewAdd : TextView = findViewById(R.id.text_result)
         val result = textViewAdd.text.toString().toInt()+1

        textViewAdd.text= result.toString()
    }
}