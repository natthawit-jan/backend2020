package com.natwit442.project1.natwit442project1

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.util.concurrent.ConcurrentHashMap


@SpringBootApplication
@EnableCaching
class Natwit442Project1Application {

	@Bean
	fun restTemplate(): RestTemplate? {
		return RestTemplate()
	}


	@Bean
	fun getConcurrentHashMap(): ConcurrentHashMap<String, String> {
		return ConcurrentHashMap()
	}


	private val LOGGER = LoggerFactory.getLogger(Natwit442Project1Application::class.java)
	@Bean
	fun runner(restTemplate: RestTemplate): CommandLineRunner = object : CommandLineRunner {
		override fun run(vararg args: String?) {
//			val headers = HttpHeaders()
//			headers.setAccept(listOf(MediaType.APPLICATION_JSON))
//			val entity = HttpEntity("body", headers)
//
//			val url = "http://localhost:8080/wc?target=https://nickolasfisher.com/blog/How-to-Make-Concurrent-Service-API-Calls-in-Java-Using-Spring-Boot"
//			val o = restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
//			println(o.body)


		}

	}


}

fun main(args: Array<String>) {
	runApplication<Natwit442Project1Application>(*args)
}
