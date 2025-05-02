package com.example.practicepartone

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity() {
private lateinit var video:VideoView
private lateinit var textaer:TextView
private val aerople=object:BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?) {
        val ison=intent!!.getBooleanExtra("state",false)
        textaer.text=if (ison)"aeroplane mode on" else "its off"
    }
}
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        video=findViewById(R.id.videovisible)
        textaer=findViewById(R.id.aero)
        val mediacontroller=MediaController(this)
        mediacontroller.setAnchorView(video)
        val videouri=Uri.parse("android.resource://${packageName}/${R.raw.logo3}")
        video.setMediaController(mediacontroller)
        video.setVideoURI(videouri)
        video.requestFocus()
        video.start()
        val but=findViewById<Button>(R.id.movetoanimation)
        but.setOnClickListener {
            val nnt=Intent(this,MainActivity3::class.java)
            startActivity(nnt)
        }
        val butt=findViewById<Button>(R.id.movetosql)
        butt.setOnClickListener {
            val jk=Intent(this,MainActivity6::class.java)
            startActivity(jk)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(aerople, IntentFilter(ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(aerople)
    }
}