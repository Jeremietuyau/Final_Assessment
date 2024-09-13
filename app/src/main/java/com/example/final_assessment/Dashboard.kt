package com.example.final_assessment

import EntityAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Dashboard : AppCompatActivity() {

    @Inject
    lateinit var api: Api

    private lateinit var recyclerView: RecyclerView
    private lateinit var entityAdapter: EntityAdapter
    private val entityList = mutableListOf<Entity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        entityAdapter = EntityAdapter(entityList) { entity ->
            openDetailsScreen(entity)
        }
        recyclerView.adapter = entityAdapter

        val keypass = intent.getStringExtra("KEYPASS")

        if (keypass != null) {
            fetchEntities(keypass)
        } else {
            Toast.makeText(this, "Keypass is missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchEntities(keypass: String) {
        lifecycleScope.launch {
            try {
                val response = api.getDashboardEntities(keypass)
                val entities = response.entities

                entityList.clear()  // Clear the existing list
                entityList.addAll(entities)
                entityAdapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Toast.makeText(this@Dashboard, "Failed to load entities: ${e.message}", Toast.LENGTH_LONG).show()
                // You can also log the error here
                // Log.e("Dashboard", "Failed to load entities", e)
            }
        }
    }

    private fun openDetailsScreen(entity: Entity?) {
        if (entity != null) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("ENTITY_SPORT_NAME", entity.sportName)
            intent.putExtra("ENTITY_PLAYER_COUNT", entity.playerCount)
            intent.putExtra("ENTITY_FIELD_TYPE", entity.fieldType)
            intent.putExtra("ENTITY_OLYMPIC_SPORT", entity.olympicSport)
            intent.putExtra("ENTITY_DESCRIPTION", entity.description)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Entity is null", Toast.LENGTH_SHORT).show()
        }
    }
}