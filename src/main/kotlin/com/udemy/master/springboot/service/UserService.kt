package com.udemy.master.springboot.service

import com.udemy.master.springboot.model.User
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UserService {
    private val users = mutableListOf<User>()

    init {
        users.add(User(1, "Adam", LocalDate.of(1990,1,1)))
        users.add(User(2, "Eve", LocalDate.of(1991,2,1)))
        users.add(User(3, "Jack", LocalDate.of(1993,3,1)))
    }

    fun findAll(): List<User> {
        return users
    }

    fun save(user: User): User {
        if (user.id == null) {
            val sortedUser = users.sortedByDescending { it.id }
            user.id = sortedUser.first().id!! + 1
        }
        users.add(user)
        return user
    }

    fun find(id: Int): Optional<User> {
        val user = users.find { it.id == id }
        return if (user == null) {
            Optional.empty()
        } else {
            Optional.of(user)
        }
    }

    fun delete(id: Int): Optional<User> {
        val user = users.find { it.id == id }
        return if (user != null && users.removeAll { it.id == id }) {
            Optional.of(user)
        } else {
            Optional.empty()
        }
    }
}