package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)
        val rvScores = findViewById<RecyclerView>(R.id.rvScores)
        val score = intent.getIntExtra("SCORE", 0)
        val username = intent.getStringExtra("USERNAME") ?: "Unknown"
        tvFinalScore.text = "Your final score is: $score"

        val scores = ScoreManager.getScores(this)
        rvScores.layoutManager = LinearLayoutManager(this)
        rvScores.adapter = ScoreAdapter(scores)

        btnBackToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}