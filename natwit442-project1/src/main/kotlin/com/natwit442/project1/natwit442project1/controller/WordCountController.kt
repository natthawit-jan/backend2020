package com.natwit442.project1.natwit442project1.controller



import com.natwit442.project1.natwit442project1.service.WordCountService
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class WordCountController(val wordCountService: WordCountService) {

    private val LOGGER = LoggerFactory.getLogger(WordCountController::class.java)

    @RequestMapping("/wc")
    fun computeAdd(@RequestParam(value="target")  url : String, model: Model) : String {
        LOGGER.info("Parsing ${url} for html version")
        val result = wordCountService.countWord(url)
        model.addAttribute("result", result)

        return "result"

    }
}