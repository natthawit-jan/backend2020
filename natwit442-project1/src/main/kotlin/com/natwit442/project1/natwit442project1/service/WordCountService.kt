package com.natwit442.project1.natwit442project1.service


import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class WordCountService(val asyncService: AsyncService) {



    private val LOGGER = LoggerFactory.getLogger(WordCountService::class.java)

    @Cacheable("words", condition = "#force==false", sync = true)
    fun countWord(url : String, force: Boolean) : CompletableFuture<Pair<Int, List<String>>> {
        return asyncService.service(url);

    }



}