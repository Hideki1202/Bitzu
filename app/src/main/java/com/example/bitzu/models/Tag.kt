package com.example.bitzu.models

data class Tag(

    val id: Int,
    val tag: String

) {
    override fun toString(): String {
        return tag
    }
}