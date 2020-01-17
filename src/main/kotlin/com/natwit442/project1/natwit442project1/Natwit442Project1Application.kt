package com.natwit442.project1.natwit442project1

import com.github.benmanes.caffeine.cache.Caffeine


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager

import org.springframework.context.annotation.Bean
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


@SpringBootApplication
@EnableCaching
class Natwit442Project1Application {

	@Bean(name = ["cacheBean"])
	fun cacheManager(): CacheManager {
		val messageCache: CaffeineCache = buildCache("wc-caches", 60)
		val manager = SimpleCacheManager()
		manager.setCaches(listOf(messageCache))
		return manager
	}


    private fun buildCache(name: String, minutesToExpire: Long): CaffeineCache {
        return CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
                .maximumSize(100)
                .build())
    }


    @Bean
    fun concurrentHashMapVisitedTime(): ConcurrentHashMap<String, Date> {
        return ConcurrentHashMap()
    }

    @Bean
    fun getConcurrentHashMap(): ConcurrentHashMap<String, String> {
        return ConcurrentHashMap()
    }

}

fun main(args: Array<String>) {
    runApplication<Natwit442Project1Application>(*args)
}
