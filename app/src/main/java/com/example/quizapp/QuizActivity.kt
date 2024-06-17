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

        questions = listOf(
            Question("Qui a réalisé le film 'Inception'?", listOf("Christopher Nolan", "Steven Spielberg", "James Cameron", "Quentin Tarantino"), 0, "CINEMA"),
            Question("Quel film a remporté l'Oscar du meilleur film en 2020?", listOf("Parasite", "1917", "Joker", "Once Upon a Time in Hollywood"), 0, "CINEMA"),
            Question("Qui a joué le personnage de 'Harry Potter' dans la série de films?", listOf("Daniel Radcliffe", "Elijah Wood", "Rupert Grint", "Tom Felton"), 0, "CINEMA"),
            Question("Dans quel film trouve-t-on le personnage de 'Tony Montana'?", listOf("Scarface", "Le Parrain", "Les Affranchis", "Casino"), 0, "CINEMA"),
            Question("Quel est le nom de l'ordinateur dans '2001, l'Odyssée de l'espace'?", listOf("HAL 9000", "Deep Thought", "Skynet", "JARVIS"), 0, "CINEMA"),
            Question("Quel film de Disney a pour personnage principal un lion nommé Simba?", listOf("Le Roi Lion", "Bambi", "Dumbo", "Tarzan"), 0, "CINEMA"),
            Question("Qui a réalisé le film 'Pulp Fiction'?", listOf("Quentin Tarantino", "Martin Scorsese", "Francis Ford Coppola", "Alfred Hitchcock"), 0, "CINEMA"),
            Question("Quel acteur a joué le rôle principal dans le film 'Forrest Gump'?", listOf("Tom Hanks", "Brad Pitt", "Johnny Depp", "Leonardo DiCaprio"), 0, "CINEMA"),
            Question("Dans quel film peut-on entendre la réplique 'Voici Johnny!'?", listOf("Shining", "Psychose", "Massacre à la tronçonneuse", "Scream"), 0, "CINEMA"),
            Question("Quel film de science-fiction de 1999 a été réalisé par les Wachowski?", listOf("Matrix", "Blade Runner", "Minority Report", "Terminator 2"), 0, "CINEMA"),

            Question("Qui a remporté la Coupe du Monde de football en 2018?", listOf("France", "Croatie", "Brésil", "Allemagne"), 0, "SPORT"),
            Question("Quel joueur de basketball est surnommé 'King James'?", listOf("LeBron James", "Michael Jordan", "Kobe Bryant", "Shaquille O'Neal"), 0, "SPORT"),
            Question("Dans quel sport utilise-t-on un 'putter'?", listOf("Golf", "Tennis", "Football américain", "Hockey sur glace"), 0, "SPORT"),
            Question("Quelle équipe a remporté le Super Bowl en 2020?", listOf("Kansas City Chiefs", "San Francisco 49ers", "New England Patriots", "Green Bay Packers"), 0, "SPORT"),
            Question("Quel joueur de tennis a remporté le plus de titres du Grand Chelem?", listOf("Roger Federer", "Rafael Nadal", "Novak Djokovic", "Pete Sampras"), 1, "SPORT"),
            Question("Quelle ville a accueilli les Jeux Olympiques d'été en 2016?", listOf("Rio de Janeiro", "Londres", "Tokyo", "Pékin"), 0, "SPORT"),
            Question("Quel pays a remporté le plus de médailles aux Jeux Olympiques d'été de 2021?", listOf("États-Unis", "Chine", "Japon", "Grande-Bretagne"), 0, "SPORT"),
            Question("Quel club de football a remporté le plus de Ligues des Champions?", listOf("Real Madrid", "AC Milan", "Liverpool", "Bayern Munich"), 0, "SPORT"),
            Question("Quel boxeur est surnommé 'The Greatest'?", listOf("Muhammad Ali", "Mike Tyson", "Floyd Mayweather", "Manny Pacquiao"), 0, "SPORT"),
            Question("Dans quel sport peut-on réaliser un 'triple axel'?", listOf("Patinage artistique", "Ski alpin", "Gymnastique", "Plongeon"), 0, "sport"),

            Question("Quel est le nom du héros dans la série de jeux 'The Legend of Zelda'?", listOf("Link", "Zelda", "Ganon", "Epona"), 0, "JEUX-VIDEO"),
            Question("Dans quel jeu vidéo trouve-t-on le personnage de 'Master Chief'?", listOf("Halo", "Call of Duty", "Destiny", "Gears of War"), 0, "JEUX-VIDEO"),
            Question("Quel jeu de bataille royale est développé par Epic Games?", listOf("Fortnite", "PUBG", "Apex Legends", "Warzone"), 0, "JEUX-VIDEO"),
            Question("Quel jeu vidéo met en scène un plombier nommé Mario?", listOf("Super Mario", "Sonic the Hedgehog", "Crash Bandicoot", "Donkey Kong"), 0, "JEUX-VIDEO"),
            Question("Dans quel jeu incarne-t-on un chasseur de primes nommé Samus Aran?", listOf("Metroid", "Halo", "Mass Effect", "Destiny"), 0, "JEUX-VIDEO"),
            Question("Quel jeu de rôle en ligne massivement multijoueur est souvent abrégé en 'WoW'?", listOf("World of Warcraft", "Warframe", "Guild Wars", "Final Fantasy XIV"), 0, "JEUX-VIDEO"),
            Question("Quel jeu de simulation de vie a été créé par Will Wright?", listOf("Les Sims", "SimCity", "Animal Crossing", "Stardew Valley"), 0, "JEUX-VIDEO"),
            Question("Dans quel jeu de tir trouve-t-on la carte 'Dust II'?", listOf("Counter-Strike", "Call of Duty", "Overwatch", "Valorant"), 0, "JEUX-VIDEO"),
            Question("Quel est le sous-titre de 'The Witcher 3'?", listOf("Wild Hunt", "Blood and Wine", "Hearts of Stone", "Nightmare of the Wolf"), 0, "JEUX-VIDEO"),
            Question("Quel personnage de 'Street Fighter' crie 'Hadouken'?", listOf("Ryu", "Ken", "Chun-Li", "Guile"), 0, "JEUX-VIDEO")
        )

        fun loadQuestion() {

            val categ = intent.getStringExtra("CATEGORY");

            val questionFiltered = questions.filter { it.category == categ }

            if (currentQuestionIndex < questionFiltered.size) {
                val question = questionFiltered[currentQuestionIndex]
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