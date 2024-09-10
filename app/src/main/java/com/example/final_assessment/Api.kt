package com.example.final_assessment

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface Api {
    @POST("/footscray/auth")
    suspend fun login (@Body loginRequest: LoginRequest): LoginResponse

    @GET("/dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse

}