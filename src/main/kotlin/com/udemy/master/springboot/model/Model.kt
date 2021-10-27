package com.udemy.master.springboot.model

import java.time.LocalDate
import javax.validation.constraints.Past
import javax.validation.constraints.Size

data class User(var id: Int?,
                @field:Size(min = 2, message = "Name to short")
                var name: String,
                @field:Past
                var birthDate: LocalDate) {
    constructor(name: String, birthDate: LocalDate): this(null, name, birthDate)
}