package com.natwit442.project1.natwit442project1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class Natwit442Project1Application

fun main(args: Array<String>) {
	runApplication<Natwit442Project1Application>(*args)
}
