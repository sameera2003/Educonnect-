package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btn_studentlogin=findViewById<Button>(R.id.button_studentlogin)
        val btn_stafflogin=findViewById<Button>(R.id.buttonstafflogin)

        btn_studentlogin.setOnClickListener {
            val intent = Intent(this,Student_loginActivity::class.java)
            startActivity(intent)
        }
        btn_stafflogin.setOnClickListener {
            val intent = Intent(this,StaffloginActivity::class.java)
            startActivity(intent)
        }
    }

}