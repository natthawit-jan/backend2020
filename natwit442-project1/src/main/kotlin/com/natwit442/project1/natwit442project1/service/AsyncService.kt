package com.natwit442.project1.natwit442project1.service

import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async

import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class AsyncService {
    private val LOGGER = LoggerFactory.getLogger(AsyncService::class.java)


    @Async
    fun service(url: String): CompletableFuture<Pair<Int, List<String>>> {

        try {
            Jsoup.connect(url).get().run {
                val counter = mutableMapOf<String, Int>();
                val matchThis = Regex("(?<![-])\\b[a-zA-Z]{1,20}\\b(?![-])", RegexOption.IGNORE_CASE);

                val setOfWord = matchThis.findAll(body().text())
                setOfWord.forEach {
                    val lower = it.value.toLowerCase()

                    if (!counter.containsKey(lower)) {
                        counter[lower] = 1

                    } else {
                        val value = counter[lower]
                        counter[lower] = value!!.plus(1);
                    }
                }


                val sumOfWords = counter.values.sum()
                LOGGER.info("Sum = ${sumOfWords}")
                val orderValue = counter
                        .toList()
                        .sortedByDescending { (_, value) -> value }

                var topTen: List<String>;
                topTen = if (orderValue.size < 10) {
                    orderValue.subList(0, orderValue.size).map { value ->  value.first}

                } else {
                    orderValue.subList(0, 10).map { value ->  value.first}
                }
                LOGGER.info("Done computing the value -> return ${topTen}")
                return CompletableFuture.completedFuture(Pair(sumOfWords, topTen))
            }

        } catch (error: Error) {
            println(error);
        }

        return CompletableFuture.completedFuture(Pair(0, listOf()))
    }

}