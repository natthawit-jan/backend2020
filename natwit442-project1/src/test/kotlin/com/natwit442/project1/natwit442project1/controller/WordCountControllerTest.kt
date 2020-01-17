package com.natwit442.project1.natwit442project1.controller


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class WordCountControllerTest {


    @LocalServerPort
    private val port = 0

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @Autowired
    private val wordCountController: WordCountController? = null


    @Test
    fun canMakeASimpleCall() {

        //and do I need this JSON media type for my use case?
        //and do I need this JSON media type for my use case?
        assertThat(testRestTemplate).isNotNull
        val url = "http://localhost:${port}/wc?target=https://spring.io/guides/gs/testing-web/"
        val headers = HttpHeaders()
        val entity = HttpEntity<Any>(headers)
        val out: ResponseEntity<String> = testRestTemplate!!.exchange<String>(url, HttpMethod.GET, entity, String::class.java)
        assertThat(out.statusCode).isEqualTo(HttpStatus.OK)

    }


}