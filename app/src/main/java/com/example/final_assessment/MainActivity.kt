package com.example.final_assessment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
//use hilt dependencies to inject dependencies into the login activity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//declaring the 3 variable for the login
    lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var loginButton: Button

    // ViewModel injection
    //Initializes a MainViewModel instance by delegating the initialization to the viewModels() function. This injects the ViewModel using Hilt's dependency injection.
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    //initializes the variable and linking them to the corresponding views in the layout file
        //uses the id form the layout file
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.LoginButton)
    //defining the action to take when the button is clicked
        loginButton.setOnClickListener {
            //taking the user input from the text field and converting it ot string removing starting and leading white space
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
    //checking if the username field and the password field is not empty before proceeding
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Call ViewModel login method
                viewModel.login(username, password)
            } else {
                //displaying a toast message if either username or password is empty
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Observe ViewModel live data on successfull login attempt
        //verify if the keypass is not null
        viewModel.loginSuccess.observe(this, Observer { keypass ->
            if (keypass != null) {
                // Navigate to Dashboard
                val intent = Intent(this, Dashboard::class.java)
                //using intent to send the keypass reponse to the dashboard activity
                intent.putExtra("KEYPASS", keypass)
                //starting the intent activity to move to the dashboard activity
                //close the current activity so that after moving to the dashboard activity the user can't use the back button to return to the login screen
                startActivity(intent)
                finish()
            }
        })
        // Observe any error message that is emitted and displaying for a longer duration
        //observe is used to listen to any changes in for the login errors -same as kotlin coroutines (stateflow)
        viewModel.loginError.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        //added features displaying the username of the user on successfull login attempt before moving to the dashboard
        viewModel.toastMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })
    }
}