package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val username = intent.getStringExtra("USERNAME")
        tvWelcome.text = "Welcome, $username!"

        val btnCategory1 = findViewById<Button>(R.id.btnCategory1)
        btnCategory1.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("CATEGORY", "SPORT")
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        val btnCategory2 = findViewById<Button>(R.id.btnCategory2)
        btnCategory2.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("CATEGORY", "CINEMA")
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        val btnCategory3 = findViewById<Button>(R.id.btnCategory1)
        btnCategory1.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("CATEGORY", "JEUX_VIDEO")
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        // Ajoutez des listeners pour les autres catégories
    }
}