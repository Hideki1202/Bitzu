package com.example.bitzu.models;

import java.io.Serializable

data class User (

    val username: String,
    val telefone: String,
    val email: String,
    val senha: String,
    val dataNascimento: String,
    val id : String?

) : Serializable {

}

