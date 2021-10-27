package com.udemy.master.springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MasterSpringBootApplication

fun main(args: Array<String>) {
    runApplication<MasterSpringBootApplication>(*args)
}
