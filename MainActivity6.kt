package com.example.practicepartone

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity6 : AppCompatActivity() {
    private lateinit var db:SQLiteDatabase
    private lateinit var addbutton:Button
    private lateinit var deletebutton:Button
    private lateinit var updatebutton:Button
    private lateinit var viewbutton:Button
    private lateinit var detailstext:TextView
    private lateinit var name:TextView
    private lateinit var age:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main6)

        db=DbHelper(this).writableDatabase
        addbutton=findViewById(R.id.addbut)
        deletebutton=findViewById(R.id.deletebut)
        updatebutton=findViewById(R.id.updatebut)
        viewbutton=findViewById(R.id.viewbut)
        detailstext=findViewById(R.id.viewdet)
        name=findViewById(R.id.name)
        age=findViewById(R.id.age)

        addbutton.setOnClickListener {
            adddata()
        }
        deletebutton.setOnClickListener {
            deletedata()
        }
        updatebutton.setOnClickListener {
            updatedata()
        }
        viewbutton.setOnClickListener {
            viewdata()
        }

    }

    private fun viewdata() {
        val cursor=db.rawQuery("SELECT * FROM user",null)
        val data=StringBuilder()

        while(cursor.moveToNext())
        {
            val id=cursor.getInt(0)
            val nam=cursor.getString(1)
            val ag=cursor.getInt(2)
            data.append("id:${id},name:${nam},age:${ag}\n")

        }
        detailstext.text=data.toString()
    }

    private fun updatedata() {
        val n=name.text.toString()
        val a=age.text.toString().toIntOrNull()
        if (n!=null && a!=null)
        {
            val values=ContentValues().apply { put("age",a) }
            val r=db.update("user",values,"name=?", arrayOf(n))
            if (r>0)
            {
                Toast.makeText(this,"updated",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun deletedata() {
        val n=name.text.toString()
        val a=age.text.toString().toIntOrNull()
        if (n!=null && a!=null)
        {
            val r=db.delete("user","name=?", arrayOf(n))
            if (r>0)
            {
                Toast.makeText(this,"updated",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun adddata() {
        val n=name.text.toString()
        val a=age.text.toString().toIntOrNull()
        if (n!=null && a!=null)
        {
            val values=ContentValues().apply {
                put("name",n)
                put("age",a)

            }
            db.insert("user",null,values)

        }
    }
}