package com.natwit442.project1.natwit442project1.service


import org.jsoup.Connection
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap


@Service
class WordCountService(val cacheService: CacheService, val concurrentHashMap: ConcurrentHashMap<String, String>) {

    private val LOGGER = LoggerFactory.getLogger(WordCountService::class.java)

    fun makeConnection(url: String): Connection {

        return Jsoup.connect(url)

    }

    fun process(url: String, force: Boolean): Pair<Int, List<String>>? {
        // check for etag
        val conn = makeConnection(url)
        LOGGER.info("Check header for etag")
        try {
            val response = conn.execute()
            val containEtag = response.hasHeader("etag")
            if (containEtag) {
                LOGGER.info("Etag Found")
                val isEtagStoredInConcurrentMap = concurrentHashMap.containsKey(url)
                val etag = response.header("etag")
                if (isEtagStoredInConcurrentMap) {
                    val etagImMap = concurrentHashMap[url]
                    LOGGER.info("ETAG found in the map as well")
                    return if (etag == etagImMap) {
                        LOGGER.info("THE Etag is match")
                        cacheService.countWord(url, false, response)
                    } else {
                        LOGGER.info("THE Etag is not match")
                        // update the etag and request normally
                        concurrentHashMap[url] = etag
                        cacheService.countWord(url, force, response)
                    }
                } else {
                    LOGGER.info("Etag not found in map")
                    concurrentHashMap[url] = etag
                    return cacheService.countWord(url, force, response)

                }
            } else {
                return cacheService.countWord(url, force, response)
            }


        } catch (err: IOException) {
            return null
        }

    }

}