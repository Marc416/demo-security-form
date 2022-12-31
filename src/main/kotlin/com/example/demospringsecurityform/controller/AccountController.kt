package com.example.demospringsecurityform.controller

import com.example.demospringsecurityform.account.domain.entity.Account
import com.example.demospringsecurityform.account.domain.repository.AccountRepository
import com.example.demospringsecurityform.account.domain.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    val accountRepository: AccountRepository,
    val accountService: AccountService,
) {
    @GetMapping("/account/{role}/{username}/{password}")
    fun saveAccount(@ModelAttribute account: Account): Account {
        // RestController 로 인해서 jason으로 리턴
        accountService.create(account)
        return accountRepository.save(account)
    }
}