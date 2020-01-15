package com.natwit442.project1.natwit442project1.filter

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse


//@Configuration
//@Order(1)
class CheckContentBeforeMoveOn : OncePerRequestFilter() {


    private val LOGGER = LoggerFactory.getLogger(CheckContentBeforeMoveOn::class.java)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        LOGGER.info("Start doing filter ")
        val oldUrl = request.requestURI
        val map = request.parameterMap
        LOGGER.info(map.keys.toString())
        LOGGER.info(oldUrl)
        val newUrl = "${oldUrl}&force=true"
        LOGGER.info(newUrl)
        LOGGER.info("Forwarding the request becuase it requires cache")
        chain.doFilter(object : HttpServletRequestWrapper(request) {
            override fun getRequestURI(): String {
                return "something else"
            }

        }, response)
//

    }

}


//@Configuration
//@Order(2)
class CheckMore : OncePerRequestFilter() {
    private val LOGGER = LoggerFactory.getLogger(CheckMore::class.java)
    override fun doFilterInternal(p0: HttpServletRequest, p1: HttpServletResponse, p2: FilterChain) {
        LOGGER.info(p0.requestURI)
        p2.doFilter(p0, p1)

    }
}


