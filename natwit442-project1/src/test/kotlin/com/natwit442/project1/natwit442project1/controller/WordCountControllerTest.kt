package com.natwit442.project1.natwit442project1.controller


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest()
@AutoConfigureMockMvc
internal class WordCountControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null


    @Test
    fun canMakeASimpleCall() {

        //and do I need this JSON media type for my use case?
        //and do I need this JSON media type for my use case?
        mockMvc?.perform(get("/wc?target=https://spring.io/guides/gs/testing-web/"))?.andExpect(status().isOk)
        mockMvc?.perform(get("/wc?target=https://spring.io/guides/gs/testing-web/"))?.andExpect(status().isOk)
        mockMvc?.perform(get("/wc?target=https://spring.io/guides/gs/testing-web/"))?.andExpect(status().isOk)
        mockMvc?.perform(get("/wc?target=https://spring.io/guides/gs/testing-web/"))?.andExpect(status().isOk)


    }


}