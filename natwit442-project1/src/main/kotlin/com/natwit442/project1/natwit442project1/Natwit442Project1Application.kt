package com.natwit442.project1.natwit442project1

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableCaching
class Natwit442Project1Application {

	private val LOGGER = LoggerFactory.getLogger(Natwit442Project1Application::class.java)
	@Bean
	fun runner() : CommandLineRunner = object : CommandLineRunner {
		override fun run(vararg args: String?) {

		}

	}
}

fun main(args: Array<String>) {
	runApplication<Natwit442Project1Application>(*args)
}
