package com.natwit442.project1.natwit442project1.error

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestErrorHandler : ResponseEntityExceptionHandler() {


    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, "Missing the parameter ${ex.parameterName} of type ${ex.parameterType}", ex)
        val accept = request.getHeader("accept")
        return when (accept) {
            "text/plain" -> {
                ResponseEntity("The parameter ${ex.parameterName} is missing", HttpStatus.BAD_REQUEST)
            }
            "application/json" -> {
                ResponseEntity(apiError, apiError.status!!)

            }
            else -> {
                ResponseEntity("Missing parameter ${ex.parameterName}", HttpStatus.BAD_REQUEST)

            }
        }

    }

    @ExceptionHandler(JsoupNetworkError::class)
    protected fun handleNetWorkErrorFromJsoup(ex: JsoupNetworkError, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity("Network failed", HttpStatus.INTERNAL_SERVER_ERROR)

    }


}