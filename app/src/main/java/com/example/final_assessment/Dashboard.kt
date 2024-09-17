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

//mark this activity for dependency injection using hilt enabling injection of dependencies
@AndroidEntryPoint
class Dashboard : AppCompatActivity() {
    //injecting the api instance into the class using dagger hilt
    //variable will be initialized later
    @Inject
    lateinit var api: Api
    //referencing the recyclerview ui component displaying a list of entities
    private lateinit var recyclerView: RecyclerView
    //instance to bind the entitylist to the recycleview
    private lateinit var entityAdapter: EntityAdapter
    //list of entity object that will store the data to be displayed in the recyclerview
    private val entityList = mutableListOf<Entity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        // retrieving the recyclerview from the layout file
        recyclerView = findViewById(R.id.recyclerView)
        //setting a linear layout manager for verticle scrolling in the recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        //Initializes the EntityAdapter, passing the entityList and a lambda function that is called when an entity is clicked, which navigates to the details screen
        entityAdapter = EntityAdapter(entityList) { entity ->
            openDetailsScreen(entity)
        }
        //Sets the adapter for the RecyclerView, which binds data from entityList to the view.
        recyclerView.adapter = entityAdapter

//retrieve the keypass from the login page
        val keypass = intent.getStringExtra("KEYPASS")
//checking if the keypass is null and if not continue by calling the fetchentity function to load the data from the api
        if (keypass != null) {
            fetchEntities(keypass)
            //if the keypass is null, display a toast message indicating that the keypass is missing
        } else {
            Toast.makeText(this, "Keypass is missing", Toast.LENGTH_SHORT).show()
        }
    }
//creating the function to take the keypass and launches a coroutine within the lifecyclescope
    private fun fetchEntities(keypass: String) {
        lifecycleScope.launch {
            try {
                //call the api with the keypass to retrieve the entities
                val response = api.getDashboardEntities(keypass)
                //extracts the entities from the api response
                val entities = response.entities
                //clears any existing data in the entitylist
                entityList.clear()
                //add the new entities to the entitylist
                entityList.addAll(entities)
                //notify the adapter that the data has changed and refresh the diplayed list
                entityAdapter.notifyDataSetChanged()
            //catch any exceptions that may occurs while the data is being fetch
            } catch (e: Exception) {
                //display a toast message with the error details if fetching the data fails
                Toast.makeText(
                    this@Dashboard,
                    "Failed to load entities: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()


            }
        }
    }
            //create a function that navigate to the detail screen when an entity is clicked
    private fun openDetailsScreen(entity: Entity?) {

        if (entity != null) {
            //create an intent to navigate to the detail activity
            val intent = Intent(this, DetailsActivity::class.java)
            //passing various details about the selected entity to the detail activity
            intent.putExtra("ENTITY_SPORT_NAME", entity.sportName)
            intent.putExtra("ENTITY_PLAYER_COUNT", entity.playerCount)
            intent.putExtra("ENTITY_FIELD_TYPE", entity.fieldType)
            intent.putExtra("ENTITY_OLYMPIC_SPORT", entity.olympicSport)
            intent.putExtra("ENTITY_DESCRIPTION", entity.description)
            //starting the detail activity with the given intent
            startActivity(intent)
            //display an error message is the entity is null
        } else {
            Toast.makeText(this, "File is empty", Toast.LENGTH_SHORT).show()
        }
    }
}