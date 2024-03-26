package com.example.camp_sign_compose

fun inputValidation(vararg strings: String): Boolean {
    strings.forEach { string ->
        if(string.trim().isEmpty()) return false
    }
    return true
}