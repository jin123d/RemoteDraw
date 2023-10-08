package com.github.carver.remotedraw

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.carver.remotedraw.lib.ProcessSurfaceView

class MainActivity : AppCompatActivity() {
    private var view: ProcessSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view = findViewById(R.id.surface_view)
        findViewById<Button>(R.id.btn_next).setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }

    override fun onBackPressed() {
        view?.back()
    }
}