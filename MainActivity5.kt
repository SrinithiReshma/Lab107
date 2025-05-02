package com.example.practicepartone

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest.*
import android.health.connect.datatypes.ExerciseRoute
import android.location.Geocoder
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class MainActivity5 : AppCompatActivity() {
    private val CHANNEL_ID="simple channel"
    private val NOTIFICATION_ID=1
    private val SEND_SMS_CODE=100
    private lateinit var sendmsg:Button
    private lateinit var fusedloc:FusedLocationProviderClient
    private lateinit var loctext:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5)
        fusedloc= LocationServices.getFusedLocationProviderClient(this)

        val but=findViewById<Button>(R.id.locationfetch)
        but.setOnClickListener {
            showLocation()
        }
        loctext=findViewById(R.id.locationtext)


        val msgtext=findViewById<EditText>(R.id.msgtext)
        val phoneno=findViewById<EditText>(R.id.mobileno)
        sendmsg=findViewById(R.id.sendmsgbutton)
        sendmsg.setOnClickListener {
            val textmsg=msgtext.text.toString()
            val phone=phoneno.text.toString()
            if (textmsg.isNotEmpty() && phone.isNotEmpty())
            {
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),SEND_SMS_CODE)

                }
                else{
                    sendMessage(textmsg,phone)
                }
            }
            else{
                Toast.makeText(this,"filll both the field",Toast.LENGTH_SHORT).show()

            }
        }
        createNotification()
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
        {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),1)
                showNotification()
            }
        }
        else{
            showNotification()
        }
        showNotification()
        val toolview=findViewById<Toolbar>(R.id.toolview)
        setSupportActionBar(toolview)
        val butpopup=findViewById<Button>(R.id.popupbutton)
        butpopup.setOnClickListener {
            val popupmenus=PopupMenu(this,butpopup)
            popupmenus.menuInflater.inflate(R.menu.popupmenu,popupmenus.menu)
            popupmenus.setOnMenuItemClickListener {
                item->
                when(item.itemId)
                {
                    R.id.ok ->
                    {
                        Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.notok ->
                    {
                        Toast.makeText(this,"not ok",Toast.LENGTH_SHORT).show()
                        true

                    }

                    else ->false

                }
            }
            popupmenus.show()
        }


    }

    private fun showLocation() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1001)

        }
        fusedloc.lastLocation.addOnSuccessListener {
            location->
            if (location!=null)
            {
                Toast.makeText(this,"${location.latitude},${location.longitude}",Toast.LENGTH_SHORT).show()
                getAddress(location.latitude,location.longitude)

            }
            else{
                Toast.makeText(this,"No location",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val geocodder=Geocoder(this, Locale.getDefault())
        try {

            val addresses=geocodder.getFromLocation(latitude,longitude,1)
            if (addresses!=null)
            {
                val add=addresses[0]
                val adres=add.getAddressLine(0)
                loctext.text="$adres"

            }
            else{
                Toast.makeText(this,"no location found",Toast.LENGTH_SHORT).show()

            }
        }
        catch (e:Exception)
        {
            Toast.makeText(this,"no location found",Toast.LENGTH_SHORT).show()

        }

    }

    private fun sendMessage(textmsg: String, phone: String) {
        try {
            val sms=SmsManager.getDefault()
            sms.sendTextMessage(phone,null,textmsg,null,null)
            Toast.makeText(this,"Sending sms",Toast.LENGTH_SHORT).show()

        }
        catch(e:Exception)
        {
            e.printStackTrace()
            Toast.makeText(this,"error in sms",Toast.LENGTH_SHORT).show()

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==SEND_SMS_CODE && grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            sendmsg.performClick()

        }
        else
        {
            Toast.makeText(this,"error in Sending sms",Toast.LENGTH_SHORT).show()

        }
    }



    @SuppressLint("MissingPermission")
    private fun showNotification() {
        val builder=NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.notify)
            .setContentTitle("Notification")
            .setContentTitle("notifying you ")
        with(NotificationManagerCompat.from(this))
        {
            notify(NOTIFICATION_ID,builder.build())
        }
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            val name="notification"
            val descriptiontext="simple description"
            val importance=NotificationManager.IMPORTANCE_DEFAULT
            val channel=NotificationChannel(CHANNEL_ID,name,importance).apply { description=descriptiontext }
            val notificationmanager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationmanager.createNotificationChannel(channel)
        }}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.settings ->
            {
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.notification->
            {
                Toast.makeText(this,"Notification",Toast.LENGTH_SHORT).show()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}