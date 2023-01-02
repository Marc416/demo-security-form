package com.example.demospringsecurityform.controller

import com.example.demospringsecurityform.account.domain.entity.Account
import com.example.demospringsecurityform.account.domain.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

// thymeleaf 라이브러리가 @Controller 를 해석해서 템플릿으로 보내려함
// 그래서 account 계정을 json으로 리턴하고 싶은데 리턴이 되지않았던 것임
@RestController
class AccountController(
    val accountService: AccountService
) {
    @GetMapping("/account/{role}/{username}/{password}")
    fun saveAccount(
    @ModelAttribute account: Account) :Account{
        println()
        // RestController 로 인해서 json으로 리턴
        return accountService.create(account)
    }
}