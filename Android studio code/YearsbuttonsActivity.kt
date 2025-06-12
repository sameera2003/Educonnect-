package com.example.project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class YearsbuttonsActivity : AppCompatActivity() {

    // Replace these with your Google Drive folder links
    private val driveLinks = mapOf(
        "first" to "https://drive.google.com/drive/folders/1zdC2bakCc9BWOuKAq8cb1u0qTjItIVjG?usp=sharing",
        "second" to "https://drive.google.com/drive/folders/1tRsMN3drQEG7OUCGf1IDMIW77F3bJk1E?usp=drive_link",
        "third" to "https://drive.google.com/drive/folders/13aBPdeYFCe1dcm54DFOo1UF2N3OPEK-O?usp=drive_link",
        "fourth" to "https://drive.google.com/drive/folders/17E2tw6Ya7JfZYDdWdtZ6bVbdsmzvV3Aa?usp=drive_link"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yearsbuttons)

        findViewById<Button>(R.id.btnfirst).setOnClickListener { openDriveFolder("first") }
        findViewById<Button>(R.id.btnsecond).setOnClickListener { openDriveFolder("second") }
        findViewById<Button>(R.id.btnthird).setOnClickListener { openDriveFolder("third") }
        findViewById<Button>(R.id.btnfourth).setOnClickListener { openDriveFolder("fourth") }
    }

    private fun openDriveFolder(year: String) {
        val driveLink = driveLinks[year]
        if (driveLink != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(driveLink))
            startActivity(intent)
        }
    }
}