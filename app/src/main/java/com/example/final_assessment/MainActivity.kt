package com.example.final_assessment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etPassword: EditText
    private lateinit var etUsername: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val loginResponse = apiService.login(LoginRequest(username, password))
                val keypass = loginResponse.keypass

                // Switch back to Main thread for UI operations
                withContext(Dispatchers.Main) {
                    showWelcomeFragment(username)

                    // Delay on IO thread to avoid blocking the main thread
                    withContext(Dispatchers.IO) {
                        delay(2000)  // Wait for 2 seconds
                    }

                    // Now move to the dashboard on the main thread after delay
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@MainActivity, Dashboard::class.java)
                        intent.putExtra("KEYPASS", keypass)
                        startActivity(intent)
                        finish()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showWelcomeFragment(username: String) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val welcomeFragment = Welcomescreen.newInstance(username)
        fragmentTransaction.replace(R.id.fragmentContainer, welcomeFragment)
        fragmentTransaction.commit()
    }
}
