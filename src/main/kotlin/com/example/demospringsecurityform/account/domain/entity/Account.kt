package com.example.demospringsecurityform.account.domain.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Account {

    @Id
    @GeneratedValue
    val id: Int = 0
    lateinit var username: String
    lateinit var password: String
    lateinit var role: String

    fun encodePassword() {
        password = "{noop}$password"
    }
}