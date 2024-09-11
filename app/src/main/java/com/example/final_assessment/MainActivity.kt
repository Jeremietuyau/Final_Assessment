package com.example.final_assessment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etPassword: EditText
    private lateinit var etUsername: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.LoginButton)

        loginButton.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                login(username, password)
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(username: String, password: String) {
        val apiService = RetrofitInstance.retrofit.create(Api::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val loginResponse = apiService.login(LoginRequest(username, password))
                val keypass = loginResponse.keypass

                // Now move to the dashboard
                val intent = Intent(this@MainActivity, Dashboard::class.java)
                intent.putExtra("KEYPASS", keypass)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                Log.e("MainActivity", "Login failed", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
