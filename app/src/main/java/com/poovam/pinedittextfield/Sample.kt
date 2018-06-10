package com.poovam.pinedittextfield

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class Sample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        val pinField = findViewById<PinField>(R.id.pinField)
        pinField.onTextCompleteListener = object : PinField.OnTextCompleteListener{
            override fun onTextComplete(enteredText: String) {
                Toast.makeText(this@Sample,enteredText,Toast.LENGTH_SHORT).show()
            }

        }
    }
}
