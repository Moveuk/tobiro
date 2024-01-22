package com.sparta.tobiro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TobiroApplication

fun main(args: Array<String>) {
    runApplication<TobiroApplication>(*args)
}
