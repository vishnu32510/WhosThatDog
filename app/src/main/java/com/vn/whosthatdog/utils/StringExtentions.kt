package com.vn.whosthatdog.utils

fun String.capitalizeFirstLetter(): String{
    return this.replaceFirstChar { it.uppercase() }
}