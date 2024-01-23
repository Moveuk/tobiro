package com.sparta.tobiro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TobiroApplication

fun main(args: Array<String>) {
    runApplication<TobiroApplication>(*args)
}
