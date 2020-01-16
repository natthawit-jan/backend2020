package com.natwit442.project1.natwit442project1.service

import com.natwit442.project1.natwit442project1.error.JsoupNetworkError
import org.jsoup.Connection
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.net.UnknownHostException

@Service
class CacheService {
    private val LOGGER = LoggerFactory.getLogger(CacheService::class.java)

    @Cacheable(value = ["wc-caches"], condition = "#force!=true", sync = true, key = "#p0")
    fun countWord(url: String, force: Boolean, res: Connection): Pair<Int, List<String>> {
        LOGGER.info("Result is not in the cache lemme compute")
        return try {
            service(res.execute().parse())
        } catch (err: UnknownHostException) {
            throw JsoupNetworkError("Can't connect")

        }

    }

    fun service(doc: Document): Pair<Int, List<String>> {


        val counter = mutableMapOf<String, Int>()
        val matchThis = Regex("(?<![-])\\b[a-zA-Z]{1,20}\\b(?![-])", RegexOption.IGNORE_CASE)
        val setOfWord = matchThis.findAll(doc.body().text())
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


    @Cacheable(value = ["wc-caches"], condition = "#force!=true", sync = true, key = "#p0")
    fun computeEtagAndCountWord(url: String, force: Boolean, res: Connection.Response): Pair<Int, List<String>> {
        return service(res.parse())
    }


}