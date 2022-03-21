package com.example.calculator

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import java.io.File

class ShowImageActivity : AppCompatActivity() {
    private lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        val message = intent.getStringExtra("Image_Uri")
        val show = findViewById<ImageView>(R.id.ImageView_Show)
        show.setImageURI(message?.toUri())
        preference = this.getSharedPreferences("sharedPrefFile", MODE_PRIVATE)


        val delete = findViewById<Button>(R.id.DeleteImage)
        delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this, Hidden::class.java)
                    preference.edit().putBoolean(Dlt,true).apply()
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, _ ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}

