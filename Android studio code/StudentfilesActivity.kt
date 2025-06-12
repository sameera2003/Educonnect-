package com.example.project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StudentfilesActivity : AppCompatActivity() {

    private lateinit var storageRef: StorageReference
    private lateinit var fileListView: ListView
    private lateinit var fileUrls: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentfiles)

        fileListView = findViewById(R.id.fileListView)
        storageRef = FirebaseStorage.getInstance().reference

        val year = intent.getStringExtra("YEAR") ?: "first" // default to first year
        listFiles(year)
    }

    private fun listFiles(year: String) {
        val yearRef = storageRef.child(year)
        fileUrls = mutableListOf()

        yearRef.listAll()
            .addOnSuccessListener { listResult ->
                val fileNames = listResult.items.map { it.name }
                fileUrls.addAll(listResult.items.map { it.downloadUrl.toString() })

                val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames)
                fileListView.adapter = adapter

                fileListView.setOnItemClickListener { _, _, position, _ ->
                    downloadFile(fileNames[position], year)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to list files: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("FirebaseList", "Error: ${e.message}")
            }
    }

    private fun downloadFile(fileName: String, year: String) {
        val fileRef = storageRef.child("$year/$fileName")
        fileRef.downloadUrl.addOnSuccessListener { uri ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()))
            startActivity(intent)
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
