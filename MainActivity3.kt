package com.example.practicepartone

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        val buttonprac=findViewById<Button>(R.id.movetoprac)
        buttonprac.setOnClickListener {
            val intr=Intent(this,MainActivity5::class.java)
            startActivity(intr)
        }
        val imgeview=findViewById<ImageView>(R.id.animationimage)
        imgeview.setBackgroundResource(R.drawable.animation)
        val ani=imgeview.background as AnimationDrawable
        imgeview.post{ani.start()}
        val buttongr=findViewById<Button>(R.id.movetographics)
        buttongr.setOnClickListener {
            val inth=Intent(this,MainActivity4::class.java)
            startActivity(inth)
        }

    }
}