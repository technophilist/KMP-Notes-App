package com.example.notes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform