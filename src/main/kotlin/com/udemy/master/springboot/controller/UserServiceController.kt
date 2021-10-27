package com.udemy.master.springboot.controller

import com.udemy.master.springboot.model.User
import com.udemy.master.springboot.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.endpoint.web.reactive.WebFluxEndpointHandlerMapping
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserServiceController @Autowired constructor(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(UserServiceController::class.java)

    @GetMapping(path = ["", "/"])
    fun getAll(): Flux<User> {
        logger.info("Get all users")
        return Flux.fromStream(userService.findAll().stream())
    }

    @PostMapping(path = ["", "/"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@Valid @RequestBody user: User): Mono<ResponseEntity<User>> {
        logger.info("Create user")
        return Mono.fromCallable {
            if (user.id == null) {
                userService.save(user)
            } else {
                throw InvalidInputException("User may not have an assigned ID")
            }
        }.subscribeOn(Schedulers.boundedElastic()).map {
            ResponseEntity.created(UriComponentsBuilder.fromPath("/users/${it.id}").build().toUri()).body(it)
        }
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): Mono<User> {
        logger.info("Get user for id : $id")
        return Mono.fromCallable {
            val user = userService.find(id)
            if (user.isPresent) {
                user.get()
            } else {
                throw UserNotFoundException("User for id $id not found")
            }
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    fun deleteUser(@PathVariable id: Int): Mono<User> {
        return Mono.fromCallable {
            val deletedUser = userService.delete(id)
            if (deletedUser.isPresent) {
                deletedUser.get()
            } else {
                throw UserNotFoundException("User for id $id not found to delete")
            }
        }
    }
}

