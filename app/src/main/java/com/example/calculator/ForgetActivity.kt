package com.example.calculator

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class ForgetActivity : AppCompatActivity() {

    private lateinit var share: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)
        share = this.getSharedPreferences("sharedPrefFile", MODE_PRIVATE)

        val btn = findViewById<Button>(R.id.ForgetEnterKey)
        val chk = findViewById<EditText>(R.id.ForgetEditText)
        btn.setOnClickListener {
            if (chk.text.toString() == share.getString(Key,"")){
                share.edit().putBoolean(MainEntry,false).apply()
                val intent = Intent(this, LoginHide::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Entered Wrong Key",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}