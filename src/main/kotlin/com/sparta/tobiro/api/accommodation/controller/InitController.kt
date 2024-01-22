package com.sparta.tobiro.api.accommodation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class InitController {

    @GetMapping
    fun test(): String {
        return "init"
    }
}