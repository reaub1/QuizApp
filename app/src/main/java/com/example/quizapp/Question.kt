package com.example.quizapp

data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val category: String
)