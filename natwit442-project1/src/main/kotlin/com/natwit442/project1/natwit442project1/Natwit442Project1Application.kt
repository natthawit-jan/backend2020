package com.natwit442.project1.natwit442project1

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Ticker
import com.natwit442.project1.natwit442project1.view.JsonViewResolver
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


@SpringBootApplication
@EnableCaching
class Natwit442Project1Application {

	@Bean
	fun restTemplate(): RestTemplate {
		return RestTemplate()
	}

	@Bean(name = ["cacheBean"])
	fun cacheManager(ticker: Ticker): CacheManager {
		val messageCache: CaffeineCache = buildCache("wc-caches", ticker, 60)
		val manager = SimpleCacheManager()
		manager.setCaches(listOf(messageCache))
		return manager
	}

	@Bean
	fun jsonViewResolver() = JsonViewResolver()


	@Bean
	fun contentNegotiatingViewResolver(manager: ContentNegotiationManager): ViewResolver {
		val resolver = ContentNegotiatingViewResolver()
		resolver.contentNegotiationManager = manager
		val resolvers: MutableList<ViewResolver> = ArrayList()
		resolvers.add(jsonViewResolver())
		resolver.viewResolvers = resolvers
		return resolver
	}

	@Bean
	fun concurrentHashMapVisitedTime(): ConcurrentHashMap<String, Date> {
		return ConcurrentHashMap()
	}

	private fun buildCache(name: String, ticker: Ticker, minutesToExpire: Long): CaffeineCache {
		return CaffeineCache(name, Caffeine.newBuilder()
				.expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
				.maximumSize(100)
				.ticker(ticker)
				.build())
	}

	@Bean
	fun ticker(): Ticker? {
		return Ticker.systemTicker()
	}


	@Bean
	fun getConcurrentHashMap(): ConcurrentHashMap<String, String> {
		return ConcurrentHashMap()
	}


	@Bean
	fun runner(restTemplate: RestTemplate): CommandLineRunner = object : CommandLineRunner {
		override fun run(vararg args: String?) {
		}

	}


}

fun main(args: Array<String>) {
	runApplication<Natwit442Project1Application>(*args)
}
