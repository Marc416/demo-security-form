package com.example.demospringsecurityform.controller

import com.example.demospringsecurityform.account.domain.entity.Account
import com.example.demospringsecurityform.account.domain.entity.AccountContext
import com.example.demospringsecurityform.account.domain.repository.AccountRepository
import com.example.demospringsecurityform.account.domain.service.AccountService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import java.security.Principal

//@RestController   //  thymeleaf 를 쓸경우 RestController 대신 Controller를 써야 적용된다.
@Controller
class SampleController(
    val accountService: AccountService,
    val accountRepository: AccountRepository,
) {
    @GetMapping("/")
    fun index(model: Model, principal: Principal?): String {

        if (principal == null) {
            model.addAttribute("message", "Hello Spring Security Index")
        } else {
            model.addAttribute("message", "Hello Spring Security Index" + principal.name)
        }

        // spring mvc 기본설정에 따라서 index.html 반환
        return "index"
    }

    @GetMapping("/info")
    fun info(model: Model): String {
        // Index html 에서 message 파라미터에 대한 vaalue
        model.addAttribute("message", "Hello Spring Security Info")
        // spring mvc 기본설정에 따라서 index.html 반환
        return "info"
    }

    @GetMapping("/dashboard")
    fun dashboard(model: Model, principal: Principal): String {
        // Index html 에서 message 파라미터에 대한 vaalue
        model.addAttribute("message", "dashboard" + principal.name)

        // ThreadLocal 에 Account 저장
        AccountContext.setAccount(accountRepository.findByUsername(principal.name))
        accountService.dashboard()
        // spring mvc 기본설정에 따라서 index.html 반환
        return "dashboard"
    }

    @GetMapping("/admin")
    fun admin(model: Model, principal: Principal): String {
        // Index html 에서 message 파라미터에 대한 vaalue
        model.addAttribute("message", "admin" + principal.name)
        // spring mvc 기본설정에 따라서 index.html 반환
        return "admin"
    }
}