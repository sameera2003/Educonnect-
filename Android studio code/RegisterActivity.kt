package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val idEditText = findViewById<EditText>(R.id.editText_id)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val registerButton = findViewById<Button>(R.id.buttonregister)

        registerButton.setOnClickListener {
            val id = idEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (id.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isIdValid(id)) {
                Toast.makeText(this, "Invalid ID format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPasswordValid(password)) {
                Toast.makeText(this, "Weak password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(email, password, id)
        }
    }
    private fun registerUser(email: String, password: String, id: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid == null) {
                        Toast.makeText(this, "Error: UID is null", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                    val user = hashMapOf(
                        "id" to id,
                        "email" to email
                    )

                    database.child("users").child(uid).setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
                            Log.d("RegisterActivity", "User added to database: $user")

                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.e("RegisterActivity", "Database error: ${e.message}", e)
                            Toast.makeText(this, "Database error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Log.e("RegisterActivity", "Registration failed", task.exception)
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isIdValid(id: String): Boolean {
        val idPattern = "^(\\d{2})33(1A|5A)(0[1-58]|08|12)[A-Z0-9]{2}\$"
        return id.matches(Regex(idPattern))
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
        return password.matches(Regex(passwordPattern))
    }
}


