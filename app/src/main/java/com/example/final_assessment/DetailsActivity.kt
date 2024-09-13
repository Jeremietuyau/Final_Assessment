package com.example.final_assessment

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var sportNameTextView: TextView
    private lateinit var playerCountTextView: TextView
    private lateinit var fieldTypeTextView: TextView
    private lateinit var olympicSportTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        sportNameTextView = findViewById(R.id.tvSportName)
        playerCountTextView = findViewById(R.id.tvPlayerCount)
        fieldTypeTextView = findViewById(R.id.tvFieldType)
        olympicSportTextView = findViewById(R.id.tvOlympicSport)
        descriptionTextView = findViewById(R.id.tvDescription)

        val intent = intent
        if (intent != null) {
            val sportName = intent.getStringExtra("ENTITY_SPORT_NAME")
            val playerCount = intent.getIntExtra("ENTITY_PLAYER_COUNT", 0)
            val fieldType = intent.getStringExtra("ENTITY_FIELD_TYPE")
            val olympicSport = intent.getBooleanExtra("ENTITY_OLYMPIC_SPORT", false)
            val description = intent.getStringExtra("ENTITY_DESCRIPTION")

            sportNameTextView.text = sportName
            playerCountTextView.text = " $playerCount"
            fieldTypeTextView.text = " $fieldType"
            olympicSportTextView.text = " ${if (olympicSport) "Yes" else "No"}"
            descriptionTextView.text = description
        } else {
            // Handle the case where intent is null
            // You can display an error message or take alternative actions
            sportNameTextView.text = "No data available"
            playerCountTextView.text = ""
            fieldTypeTextView.text = ""
            olympicSportTextView.text = ""
            descriptionTextView.text = ""
        }
    }
}