package com.example.demospringsecurityform.account.domain.repository

import com.example.demospringsecurityform.account.domain.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

//@Repository
interface AccountRepository : JpaRepository<Account, String> {
    fun findByUsername(userName:String):Account
}