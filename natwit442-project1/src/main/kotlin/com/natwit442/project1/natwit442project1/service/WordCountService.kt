package com.natwit442.project1.natwit442project1.service


import com.natwit442.project1.natwit442project1.error.BadProtocolException
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Service
class WordCountService(
        val cacheService: CacheService,
        val concurrentHashMap: ConcurrentHashMap<String, String>,
        val concurrentHashMapVisitedTime: ConcurrentHashMap<String, Date>
) {


    private val LOGGER = LoggerFactory.getLogger(WordCountService::class.java)

    fun makeConnection(url: String): Connection {
        if (!url.startsWith("http", ignoreCase = true)) throw BadProtocolException("should start with http protocol")
        return Jsoup.connect(url)
    }


    fun process(url: String, force: Boolean): Pair<Int, List<String>>? {
        val conn = makeConnection(url)
        updateRequestTime(url)
        val time = concurrentHashMapVisitedTime[url]!!.time
        if (lessThanTenMin(time)) {
            return cacheService.countWord(url, force, conn)
        } else {
            LOGGER.info("The requested url is store first-time requested 10 mins ago")
            try {
                val response = conn.execute()
                val containEtag = response.hasHeader("etag")
                if (containEtag) {
                    LOGGER.info("Etag has been found!")
                    val isEtagStoredInConcurrentMap = concurrentHashMap.containsKey(url)
                    val etag = response.header("etag")
                    if (isEtagStoredInConcurrentMap) {
                        val etagImMap = concurrentHashMap[url]
                        LOGGER.info("Etag has also been found in the concurrentMap")
                        return if (etag == etagImMap) {
                            LOGGER.info("Both etags are matched!")
                            cacheService.computeEtagAndCountWord(url, false, response)
                        } else {
                            LOGGER.info("Both etags are not matched")
                            // update the etag and request normally
                            concurrentHashMap[url] = etag
                            cacheService.computeEtagAndCountWord(url, force, response)
                        }
                    } else {
                        LOGGER.info("etag's not found in concurrentMap")
                        concurrentHashMap[url] = etag
                        return cacheService.computeEtagAndCountWord(url, force, response)

                    }
                } else {
                    return cacheService.computeEtagAndCountWord(url, force, response)
                }


            } catch (err: IOException) {
                return null
            }


        }


    }

    private fun lessThanTenMin(time: Long): Boolean {
        return (Date().time - time) < 600000


    }

    private fun updateRequestTime(url: String) {

        if (!concurrentHashMapVisitedTime.containsKey(url)) {

            concurrentHashMapVisitedTime[url] = Date()
        } else {
            val get = concurrentHashMapVisitedTime[url]
            val timeOptional = get?.time
            // If that value is in longer than ten minutes, replace with the latest time
            if (timeOptional != null) {
                if (timeOptional > 600000) {
                    // remove from the map
                    concurrentHashMapVisitedTime[url] = Date()
                }
            }

        }

    }
}