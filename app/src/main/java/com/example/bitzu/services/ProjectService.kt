package com.example.bitzu.services
import com.example.bitzu.dtos.ProjectDto
import com.example.bitzu.models.Project
import com.example.bitzu.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
interface ProjectService {
    @GET("/projects")
    fun getProjects(@Header("Authorization") token: String): Call<List<Project>>
    @POST("/projects")
    fun createProject(@Body project: ProjectDto, @Header("Authorization") token: String): Call<ProjectDto>
}