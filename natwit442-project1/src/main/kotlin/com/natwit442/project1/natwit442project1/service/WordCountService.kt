package com.natwit442.project1.natwit442project1.service


import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class WordCountService {

    private val LOGGER = LoggerFactory.getLogger(WordCountService::class.java)



    @Cacheable("words")
    fun countWord(url : String) : Pair<Int, List<String>> {
        try {
            Jsoup.connect(url).get().run {
                val counter = mutableMapOf<String, Int>();
                val matchThis = Regex("(?<![-])\\b[a-zA-Z]{1,20}\\b(?![-])", RegexOption.IGNORE_CASE);

                val setOfWord = matchThis.findAll(body().text())
                setOfWord.forEach {
                    val lower = it.value.toLowerCase()

                    if (!counter.containsKey(lower)) {
                        counter[lower] = 1

                    } else{
                        val value = counter[lower]
                        counter[lower] = value!!.plus(1);
                    }
                }


                val sumOfWords = counter.values.sum()
                LOGGER.info("Sum = ${sumOfWords}")
                val orderValue = counter.toList().sortedByDescending { (_, value) -> value }.toList().subList(0,11).map { value -> value.first }
                LOGGER.info("Topten = ${orderValue}")
                return Pair(sumOfWords, orderValue)

}



        } catch (error: Error) {
            println(error);
        }

        return Pair(0, listOf())

    }
}