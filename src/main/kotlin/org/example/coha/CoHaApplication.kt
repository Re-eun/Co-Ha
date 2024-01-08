package org.example.coha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoHaApplication

fun main(args: Array<String>) {
    runApplication<CoHaApplication>(*args)
}
