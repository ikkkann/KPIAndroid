package com.example.lab4_database_usage

data class Pizza(
    var id: Long = -1,
    var name: String,
    var toppings: String,
    var diameter: Int,
    var crustType: String
){

}