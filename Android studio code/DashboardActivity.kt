package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DashboardActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Example: Files card click listener
        findViewById<CardView>(R.id.cardview_files).setOnClickListener {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                database.child("users").child(userId).child("id").get()
                    .addOnSuccessListener { snapshot ->
                        val studentId = snapshot.value.toString()
                        val intent = Intent(this, YearsbuttonsActivity::class.java)
                        intent.putExtra("STUDENT_ID", studentId)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to fetch student ID", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
        val cardView: CardView = findViewById(R.id.cardview_files)
        cardView.setOnClickListener {
            val intent= Intent(this,YearsbuttonsActivity::class.java)
            startActivity(intent)
        }
        val cardView2: CardView = findViewById(R.id.cardview_logout)
        cardView2.setOnClickListener {
            // Handle the card click here
            //Toast.makeText(this, "Card clicked", Toast.LENGTH_SHORT).show()
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        val cardView3: CardView = findViewById(R.id.cardview_Resume)
        cardView3.setOnClickListener {
            val intent= Intent(this,ResumebuildingActivity::class.java)
            startActivity(intent)
        }
    }
}