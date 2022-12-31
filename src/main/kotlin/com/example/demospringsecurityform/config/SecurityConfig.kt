package com.example.demospringsecurityform.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        // 인가
        http!!.authorizeRequests()
            .mvcMatchers("/", "/info")
            .permitAll()
            .mvcMatchers("/admin")
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated()
        // 인증
        http.formLogin()
        // http 사용 허가
        http.httpBasic()
    }
}