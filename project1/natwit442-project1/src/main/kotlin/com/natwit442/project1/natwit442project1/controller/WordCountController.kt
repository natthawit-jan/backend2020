package com.natwit442.project1.natwit442project1.controller


import org.jsoup.Jsoup
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse
import kotlin.Error

@Controller
class WordCountController {

    @RequestMapping("/wc")
    fun computeAdd(@RequestParam(value="target")  url : String, res: HttpServletResponse, model: Model ) : String {
        println("parsing $url")
        model.addAttribute("a", "b");
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

        return "result"

    }
}