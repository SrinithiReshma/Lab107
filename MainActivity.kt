package com.example.practicepartone

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mediaplayer:MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val buthar=findViewById<Button>(R.id.nexttohardware)
        buthar.setOnClickListener {
            val inthm=Intent(this,MainActivity8::class.java)
            startActivity(inthm)
        }
        val movetoshare=findViewById<Button>(R.id.movetosharedpref)
        movetoshare.setOnClickListener {
            val kj=Intent(this,MainActivity7::class.java)
            startActivity(kj)
        }
        val butmove=findViewById<Button>(R.id.movetovideo)
        butmove.setOnClickListener {
            val int= Intent(this,MainActivity2::class.java)
            startActivity(int)

        }
        val musicimage=findViewById<ImageView>(R.id.musicimage)
        musicimage.setImageResource(R.drawable.kurti)
        val playbut=findViewById<Button>(R.id.play)
        val stopbut=findViewById<Button>(R.id.stopmusic)
        mediaplayer=MediaPlayer.create(this,R.raw.ak)
        playbut.setOnClickListener {
            if (!mediaplayer.isPlaying)
            {
                mediaplayer.start()
            }
        }
        stopbut.setOnClickListener {
            mediaplayer.stop()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mediaplayer.release()
    }
}

