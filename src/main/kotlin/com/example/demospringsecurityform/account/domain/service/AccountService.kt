package com.example.demospringsecurityform.account.domain.service

import com.example.demospringsecurityform.account.domain.entity.Account
import com.example.demospringsecurityform.account.domain.repository.AccountRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val account = accountRepository.findByUsername(username!!)
        if (account != null) {
            // 인증 절차를 위해 유저를 UserDetail로 변경한다
            return User.builder()
                    .username(account.username)
                    .password(account.password)
                    .roles(account.role)
                    .build()
        } else {
            throw UsernameNotFoundException(username)
        }
    }

    fun create(account: Account): Account {
        account.encodePassword()
        return accountRepository.save(account)
    }

}