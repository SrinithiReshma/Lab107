package com.example.practicepartone

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity7 : AppCompatActivity() {
    private val SHARED_PREF="shared_pref"
    private val KEY_NAME="srinithi"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main7)
        val but=findViewById<Button>(R.id.addinsharedpref)
        val edittextinput=findViewById<EditText>(R.id.inputtext)
        val textshared=findViewById<TextView>(R.id.shareddata)

        val sharedpref=getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        but.setOnClickListener {
            val txt=edittextinput.text.toString()
            if (txt.isNotEmpty())
            {
                val alertdialogue=AlertDialog.Builder(this)
                    .setTitle("Alert!!")
                    .setMessage("DO YOU WANT TO ADD DATA")
                    .setPositiveButton("Yes"){dialoge,_ ->
                        val data=sharedpref.getString(KEY_NAME,"")
                        val txtdata="$data\n$txt"
                        sharedpref.edit().putString(KEY_NAME,txtdata).apply()
                        textshared.text=txtdata

                    }
                    .setNeutralButton("No"){dialogue,_ ->
                        dialogue.cancel()
                    }
                    .create()
                alertdialogue.show()
            }
        }




    }
}