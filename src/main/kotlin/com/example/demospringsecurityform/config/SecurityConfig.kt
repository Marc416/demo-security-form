package com.example.demospringsecurityform.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
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

    override fun configure(auth: AuthenticationManagerBuilder?) {
        super.configure(auth)
        // 메모리에 유저생성하기
        auth!!.inMemoryAuthentication()
                .withUser("joonhee")
                // `{}` 은 스프링에서 제공하는 인코딩 함수 ; noop -> no option 인코딩하지않는다
                .password("{noop}1234")
                .roles("USER")
                .and()
                .withUser("{noop}Gong")
                .password("1234")
                .roles("ADMIN")
    }
}