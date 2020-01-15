package com.natwit442.project1.natwit442project1.controller


import com.natwit442.project1.natwit442project1.service.WordCountService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class WordCountController(val wordCountService: WordCountService) {

    // TODO Failure
    // Etag
    // deployment

    private val LOGGER = LoggerFactory.getLogger(WordCountController::class.java)

    data class Result(val sumOfWords: Int, val topTenWords: List<String>)

    @RequestMapping("/wc", produces = ["application/json"])
    @ResponseBody
    fun processJson(@RequestParam(value = "target") url: String, @RequestParam(value = "force", defaultValue = "false") force: Boolean): Result {
        // getEtag -> compare against the existing one - >

        val (sumOfWords, topTen) = wordCountService.process(url, force)!!
        return Result(sumOfWords, topTen)

    }


    @RequestMapping("/wc", produces = ["text/plain"])
    @ResponseBody
    fun processPlainText(@RequestParam(value = "target") url: String, @RequestParam(value = "force", defaultValue = "false") force: Boolean): String {
        LOGGER.info("Request comes for [plain text")
        val (sumOfWords, topTen) = wordCountService.process(url, force)!!
        return Result(sumOfWords, topTen).toString()
    }

    @RequestMapping("/wc")
    fun computeAdd(@RequestParam(value = "target") url: String, @RequestParam(value = "force", defaultValue = "false") force: Boolean, model: Model): String {

        LOGGER.info("Parsing ${url} for html version")
        val result = wordCountService.process(url, force)!!

        model.addAttribute("result", result)

        return "result"

    }
}