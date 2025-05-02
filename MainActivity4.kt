package com.example.practicepartone

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(DemoClass(this))

    }
}
class DemoClass(context: Context):View(context)
{
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint=Paint()

        canvas.drawColor(Color.RED)

        paint.isAntiAlias=false
        paint.setColor(Color.WHITE)
        canvas.drawCircle(27f,23f,34f,paint)

        paint.isAntiAlias=false
        paint.setColor(Color.BLUE)
        canvas.drawRect(123f,124f,34f,43f,paint)

        paint.setColor(Color.GREEN)
        paint.textSize=45f
        canvas.save()
        canvas.rotate(34f,45f,45f)
        canvas.drawText("doing on myself",34f,78f,paint)
        canvas.restore()

    }
}