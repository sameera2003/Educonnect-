package com.example.project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ResumebuildingActivity : AppCompatActivity() {

    private lateinit var atsScoreButton: Button

    // Replace this with your Google Drive folder link for Sample Templates
    private val sampleTemplatesDriveLink = "https://drive.google.com/drive/folders/11X2SJ97L7luc8szGzEzo9Cd1f4dGpsQ9?usp=drive_link"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumebuilding)

        val btnSampleTemplate = findViewById<Button>(R.id.sample_template)
        atsScoreButton = findViewById(R.id.ats_score)

        // Open Sample Templates folder in Google Drive
        btnSampleTemplate.setOnClickListener {
            openSampleTemplatesDriveFolder()
        }

        // Open ATS scoring web app
        atsScoreButton.setOnClickListener {
            openATSWebApp()
        }
    }

    private fun openSampleTemplatesDriveFolder() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sampleTemplatesDriveLink))
        startActivity(intent)
    }
    private fun openATSWebApp() {
        val url = "https://7249-2401-4900-92d3-397-f0f3-749a-e033-db2e.ngrok-free.app" // Your Ngrok URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}
