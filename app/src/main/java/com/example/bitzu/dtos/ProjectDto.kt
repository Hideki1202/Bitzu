package com.example.bitzu.dtos

import com.example.bitzu.models.Tag

data class ProjectDto(
    val id: Int?,
    val projectName: String,
    val description: String,
    val link: String,
    val tags: List<Int>
) {

}