package com.natwit442.project1.natwit442project1.controller


import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest()
@AutoConfigureMockMvc
internal class WordCountControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    fun myFactoryRequest(url: String, target: String, force: Boolean = false, accept: MediaType = MediaType.TEXT_HTML):
            MockHttpServletRequestBuilder {
        return get(url)
                .param("target", target)
                .param("force", force.toString())
                .accept(accept)
    }

    private val TARGET = "https://spring.io/guides/gs/testing-web/"
    private val TARGET2 = "https://spring.io/guides/gs/testing-web/"
    private val TARGET3 = "https://spring.io/guides/gs/testing-web/"
    private val TARGET4 = "https://spring.io/guides/gs/testing-web/"
    private val TARGET5 = "https://www.wikipedia.org/"


    @Autowired
    private val cacheBean: CacheManager? = null


    @Test
    fun cacheHasBeenCreated() {
        assertThat(cacheBean?.getCache("wc-caches")).isNotNull
    }

    @Test
    fun canMakeASimpleCallAndCacheTheResult() {
        cacheBean!!.getCache("wc-caches")!!.clear()

        // It should not be in the cache first time it gets called
        assertThat(cacheBean.getCache("wc-caches")?.get(TARGET)).isNull()
        // Default header to text/html
        mockMvc?.perform(myFactoryRequest("/wc", TARGET, false))?.andExpect(status().isOk)
        // It should be in the cache by now
        assertThat(cacheBean.getCache("wc-caches")?.get(TARGET)).isNotNull

    }



    @Test
    fun shouldGetSimpleHTML() {
        for (work in 0..5) mockMvc?.perform(myFactoryRequest("/wc", TARGET, false))?.andExpect(status().isOk)?.andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_HTML))

    }

    @Test
    fun shouldResultInJsonTypeWithRightKeysAndNonNullValue() {
        val jsonType = MediaType.APPLICATION_JSON
        mockMvc?.perform(myFactoryRequest("/wc", TARGET2, false, jsonType))?.andExpect(content().contentTypeCompatibleWith(jsonType))
                ?.andExpect(status().isOk)?.andExpect(jsonPath("$" +
                        ".total_words").isNotEmpty)?.andExpect(jsonPath("$.top_ten").isArray)
    }


    @Test
    fun shouldGetBasicPlainText() {
        val plain = MediaType.TEXT_PLAIN
        mockMvc?.perform(myFactoryRequest("/wc", TARGET3, false, plain))?.andExpect(content().contentTypeCompatibleWith(plain))
                ?.andExpect(status().isOk)
                ?.andExpect(content().string(containsString("total words")))?.andExpect(content().string(containsString
                ("top 10 " +
                        "words")))

    }

    @Test
    fun shouldNoTBeConfusedAboutRequestAcceptTypeForTheSameSite() {

        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.TEXT_HTML))?.andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_HTML))
                ?.andExpect(status().isOk)
        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.APPLICATION_JSON))?.andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                ?.andExpect(status().isOk)
        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.TEXT_PLAIN))?.andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                ?.andExpect(status().isOk)
        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.APPLICATION_JSON))?.andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                ?.andExpect(status().isOk)
        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.TEXT_PLAIN))?.andExpect(content()
                .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                ?.andExpect(status().isOk)
        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.TEXT_HTML))?.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))?.andExpect(status().isOk)
        mockMvc?.perform(myFactoryRequest("/wc", TARGET5, false, MediaType.APPLICATION_JSON))?.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))?.andExpect(status().isOk)


    }

    @Test
    fun malFormUrlShouldBeHandled() {
        mockMvc?.perform(myFactoryRequest("/wc", "hfldsfds.com", false, MediaType.APPLICATION_JSON))?.andExpect(status().is4xxClientError)
    }

    @Test
    fun shouldInformUserThatTargetParamIsMissing() {
        mockMvc?.perform(get("/wc"))?.andExpect(status().is4xxClientError)
    }


}