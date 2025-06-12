

package com.example.project

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage

class AtsscoreActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private lateinit var suggestionsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atsscore)

        scoreTextView = findViewById(R.id.textViewAtsScore)
        suggestionsTextView = findViewById(R.id.textViewSuggestions)

        val fileUriString = intent.getStringExtra("fileUri")
        if (fileUriString != null) {
            val fileUri = Uri.parse(fileUriString)
            processResume(fileUri)
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processResume(fileUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(fileUri.toString())
        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            val resumeText = String(bytes)
            val score = calculateATSScore(resumeText)
            scoreTextView.text = "ATS Score: $score%"
            suggestionsTextView.text = getImprovementSuggestions()
        }.addOnFailureListener {
            Log.e("Firebase", "Failed to download resume", it)
        }
    }

    private fun calculateATSScore(resumeText: String): Int {
        // Placeholder for BERT and keyword matching logic from scoring.py
        // Ideally, you'd call a server endpoint or integrate Python code with an ML model
        return (Math.random() * 100).toInt()
    }

    private fun getImprovementSuggestions(): String {
        // Placeholder for improvement suggestions
        return "Suggestions:\n- Add more skills\n- Include work experience\n- Mention certifications"
    }
}
