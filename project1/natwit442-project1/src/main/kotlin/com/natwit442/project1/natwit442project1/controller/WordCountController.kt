package com.natwit442.project1.natwit442project1.controller


import org.jsoup.Jsoup
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.Error

@RestController
class WordCountController {
    @RequestMapping("/")
    fun greeting() = "greeting! from rest controller"

    @RequestMapping("/wc")
    fun computeAdd(@RequestParam(value="target")  url : String ) : ResponseEntity<HttpStatus>{
        println("parsing $url")
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
                counter.toList().sortedByDescending { (_, value) -> value }.forEach {
                    val word = it.first;
                    val count = it.second;
                    println("$word : $count")

                }


                }



        } catch (error: Error) {
            println(error);
        }

        return ResponseEntity.ok(HttpStatus.OK)

    }
}