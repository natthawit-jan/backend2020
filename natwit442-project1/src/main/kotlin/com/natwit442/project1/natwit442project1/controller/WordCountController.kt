package com.natwit442.project1.natwit442project1.controller

import com.natwit442.project1.natwit442project1.service.WordCountService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WordCountController(val wordCountService: WordCountService) {

    @RequestMapping("/wc", produces = ["text/plain"])
    @ResponseBody
    fun processPlainText(@RequestParam(value = "target") url: String, @RequestParam(value = "force", defaultValue = "false") force: Boolean): String {
        val (sumOfWords, topTen) = wordCountService.process(url, force) ?: Pair(0, listOf())
        return "total words : ${sumOfWords}, top 10 words : ${topTen}"
    }

    @RequestMapping("/wc", produces = ["text/html"])
    fun processHtml(@RequestParam(value = "target") url: String, @RequestParam(value = "force", defaultValue = "false") force: Boolean, model: Model): String {
        val (sumOfWords, topTen) = wordCountService.process(url, force) ?: Pair(0, listOf())
        model.addAttribute("total_words", sumOfWords)
        model.addAttribute("top_ten", topTen)
        return "result"
    }

    data class Result(val totalWords: Int, val TopTen: List<String>)

    @RequestMapping("/wc")
    @ResponseBody
    fun computeAdd(@RequestParam(value = "target") url: String, @RequestParam(value = "force", defaultValue = "false") force: Boolean): Result {
        val (sumOfWords, topTen) = wordCountService.process(url, force) ?: Pair(0, listOf())

        return Result(sumOfWords, topTen)
    }
}


