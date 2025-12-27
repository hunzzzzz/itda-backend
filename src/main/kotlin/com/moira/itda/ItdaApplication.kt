package com.moira.itda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ItdaApplication

fun main(args: Array<String>) {
    runApplication<ItdaApplication>(*args)
}
