package com.example.final_assessment

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// declares an Api interface
//Retrofit uses this interface to make HTTP requests.
interface Api {
    //Specifies that this method will send an HTTP POST request to the endpoint.
    // marked as suspend, meaning it is a coroutine function
    // expects a LoginRequest object, which will be sent as the request body
    @POST("/footscray/auth")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    //Specifies that this method will send an HTTP GET request to the /dashboard/{keypass} endpoint
    //binds the method parameter keypass to the {keypass} placeholder in the URL.
    @GET("/dashboard/{keypass}")
    suspend fun getDashboardEntities(@Path("keypass") keypass: String): DashboardResponse


}