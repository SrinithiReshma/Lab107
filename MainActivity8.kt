package com.example.practicepartone

import android.Manifest
import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.wifi.WifiManager
import android.os.Bundle
import android.preference.PreferenceManager.OnActivityResultListener
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity8 : AppCompatActivity() {
    val bluetoothadapter:BluetoothAdapter?=BluetoothAdapter.getDefaultAdapter()
    private val CAMERA_CODE=100
    private lateinit var imgb:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main8)
        val cambut=findViewById<Button>(R.id.capture)
        imgb=findViewById(R.id.cameraset)
        val bluebut=findViewById<Button>(R.id.bluetoothbutton)
        val wifibut=findViewById<Button>(R.id.wifibutton)
        val wifitext=findViewById<TextView>(R.id.wifidetails)
        val bluetoothtext=findViewById<TextView>(R.id.bluetoothdetails)
        bluebut.setOnClickListener {
            if (bluetoothadapter!!.isEnabled)
            {
                bluetoothtext.text="Bluetooth enabled"
            }
            else{
                bluetoothtext.text="Bluetooth not enabled"
            }
        }

        val wifimanager=applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
       wifibut.setOnClickListener {
           if (wifimanager.isWifiEnabled)
           {
               wifitext.text="wifi enabled"
           }
           else
           {
               wifitext.text="wifi not enabled"
           }

       }
        cambut.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),CAMERA_CODE)

            }
            else{
                startCamera()
            }
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,

    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val img=data?.extras?.get("data") as Bitmap
        imgb.setImageBitmap(img)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==CAMERA_CODE && grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            startCamera()
        }
        else{
            Toast.makeText(this,"error ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        val intm=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intm,CAMERA_CODE)

    }

}
