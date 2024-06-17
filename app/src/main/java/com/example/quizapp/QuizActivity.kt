package com.example.quizapp

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class QuizActivity : AppCompatActivity() {

    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0
    private var correctCount = 0
    private var incorrectCount = 0
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btnOption1 = findViewById<Button>(R.id.btnOption1)
        val btnOption2 = findViewById<Button>(R.id.btnOption2)
        val btnOption3 = findViewById<Button>(R.id.btnOption3)
        val btnOption4 = findViewById<Button>(R.id.btnOption4)
        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvCounters = findViewById<TextView>(R.id.tvCounters)

        username = intent.getStringExtra("USERNAME") ?: "Unknown"

        // Questions en dur pour la catégorie choisie
        questions = listOf(
            Question("What is the capital of France?", listOf("Paris", "London", "Berlin", "Madrid"), 0),
            Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1)
            // Ajoutez plus de questions ici
        )

        fun loadQuestion() {
            if (currentQuestionIndex < questions.size) {
                val question = questions[currentQuestionIndex]
                tvQuestion.text = question.text
                btnOption1.text = question.options[0]
                btnOption2.text = question.options[1]
                btnOption3.text = question.options[2]
                btnOption4.text = question.options[3]

                btnOption1.setBackgroundColor(Color.LTGRAY)
                btnOption2.setBackgroundColor(Color.LTGRAY)
                btnOption3.setBackgroundColor(Color.LTGRAY)
                btnOption4.setBackgroundColor(Color.LTGRAY)
            } else {
                ScoreManager.saveScore(this, PlayerScore(username, score))

                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("USERNAME", username)
                intent.putExtra("SCORE", score)
                startActivity(intent)
                finish()
            }
        }

        fun updateCounters() {
            tvCounters.text = "Correct: $correctCount, Incorrect: $incorrectCount"
        }

        fun checkAnswer(selectedIndex: Int, selectedButton: Button) {
            val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex
            if (selectedIndex == correctAnswerIndex) {
                score++
                correctCount++
                tvScore.text = "Score: $score"
                animateButtonColor(selectedButton, Color.GREEN)
            } else {
                incorrectCount++
                animateButtonColor(selectedButton, Color.RED)
            }
            updateCounters()
            currentQuestionIndex++
            selectedButton.postDelayed({
                loadQuestion()
            }, 1000)
        }

        btnOption1.setOnClickListener { checkAnswer(0, btnOption1) }
        btnOption2.setOnClickListener { checkAnswer(1, btnOption2) }
        btnOption3.setOnClickListener { checkAnswer(2, btnOption3) }
        btnOption4.setOnClickListener { checkAnswer(3, btnOption4) }

        loadQuestion()
    }

    private fun animateButtonColor(button: Button, color: Int) {
        val colorFrom = (button.background as? ColorDrawable)?.color ?: Color.LTGRAY
        val colorTo = color
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 1000 // 1 seconde
        colorAnimation.addUpdateListener { animator ->
            button.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        colorAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                button.setBackgroundColor(Color.LTGRAY)
            }
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}