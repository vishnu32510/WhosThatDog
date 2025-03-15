package com.example.whosthatdog.utils

fun String.capitalizeFirstLetter(): String{
    return this.replaceFirstChar { it.uppercase() }
}