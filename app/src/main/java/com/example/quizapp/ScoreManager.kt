package com.example.quizapp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

data class PlayerScore(val username: String, val score: Int)

object ScoreManager {
    private const val PREFS_NAME = "quiz_scores"
    private const val SCORES_KEY = "scores"

    fun saveScore(context: Context, playerScore: PlayerScore) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val scores = getScores(context).toMutableList()
        scores.add(playerScore)
        scores.sortByDescending { it.score }

        editor.putString(SCORES_KEY, serializeScores(scores))
        editor.apply()
    }

    fun getScores(context: Context): List<PlayerScore> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val scoresString = prefs.getString(SCORES_KEY, "") ?: return emptyList()
        return deserializeScores(scoresString)
    }

    private fun serializeScores(scores: List<PlayerScore>): String {
        scores.forEach {
            Log.d("ScoreManager", "Serializing username: ${it.username}, score: ${it.score}")
        }
        return scores.joinToString(separator = "|") { "${it.username},${it.score}" }
    }

    private fun deserializeScores(scoresString: String): List<PlayerScore> {
        if (scoresString.isEmpty()) return emptyList()
        return scoresString.split("|").map {
            val parts = it.split(",")
            PlayerScore(parts[0], parts[1].toInt())
        }
    }
}