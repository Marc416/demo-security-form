package com.example.demospringsecurityform.account.domain.entity

import org.springframework.security.crypto.password.PasswordEncoder
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

    fun encodePassword(passwordEncoder: PasswordEncoder) {

        // password = "{noop}$password" // 패스워드 인코더로 인코딩
        password = passwordEncoder.encode(password)
        print(password)
    }
}