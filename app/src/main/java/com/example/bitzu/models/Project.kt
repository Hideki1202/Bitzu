package com.example.bitzu.models

data class Project(
    val id: Int?,
    val projectName: String,
    val description: String,
    val link: String,
    val tags: List<Tag>
) {

}