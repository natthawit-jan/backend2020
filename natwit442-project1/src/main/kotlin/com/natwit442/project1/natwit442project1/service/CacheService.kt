package com.natwit442.project1.natwit442project1.service

import org.jsoup.Connection
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["wc-caches"])
class CacheService {
    private val LOGGER = LoggerFactory.getLogger(CacheService::class.java)

    @Cacheable(condition = "#force!=true", sync = true, key = "#p0")
    fun countWord(url: String, force: Boolean, res: Connection.Response): Pair<Int, List<String>> {
        LOGGER.info("Result is not in the cache lemme compute")
        val result = service(res)
        return result ?: // something to return when there a null
        Pair(0, listOf())

    }

    fun service(res: Connection.Response): Pair<Int, List<String>>? {
        try {
            res.parse().run {
                val counter = mutableMapOf<String, Int>()
                val matchThis = Regex("(?<![-])\\b[a-zA-Z]{1,20}\\b(?![-])", RegexOption.IGNORE_CASE)

                val setOfWord = matchThis.findAll(body().text())
                setOfWord.forEach {
                    val lower = it.value.toLowerCase()

                    if (!counter.containsKey(lower)) {
                        counter[lower] = 1

                    } else {
                        val value = counter[lower]
                        counter[lower] = value!!.plus(1)
                    }
                }


                val sumOfWords = counter.values.sum()
                LOGGER.info("Sum = ${sumOfWords}")
                val orderValue = counter
                        .toList()
                        .sortedByDescending { (_, value) -> value }

                val topTen: List<String>
                topTen = if (orderValue.size < 10) {
                    orderValue.subList(0, orderValue.size).map { value -> value.first }

                } else {
                    orderValue.subList(0, 10).map { value -> value.first }
                }
                LOGGER.info("Done computing the value -> return ${topTen}")
                return Pair(sumOfWords, topTen)
            }

        } catch (error: Error) {
            println(error)
            return null
        }

    }


}