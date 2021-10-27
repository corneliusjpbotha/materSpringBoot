package com.udemy.master.springboot.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import java.lang.StringBuilder

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidInputException::class)
    fun handleInvalidInput(e: InvalidInputException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleBindException(e: WebExchangeBindException): ResponseEntity<String> {
        val sb = StringBuilder("Binding Errors ::\n")
        e.bindingResult.allErrors.forEach {
            sb.append("\tClass:: ${it.objectName}\n")
            sb.append("\tMessage:: ${it.defaultMessage}")
        }
        return ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST)
    }
}

class UserNotFoundException(msg: String): Exception(msg)

class InvalidInputException(msg: String): Exception(msg)