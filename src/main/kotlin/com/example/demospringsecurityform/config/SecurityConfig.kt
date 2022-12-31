package com.example.demospringsecurityform.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        // 인가
        http!!.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**")
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

    // String 암호화 인스턴스
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // Encrypt
        // ex) "{bcrypt}$2a$10$MNUy4wYOFKNV2v0LV212Qusv6I8zKluIjPYvji/nzvfHVAv/7pBcK"
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()


        // return NoOpPasswordEncoder.getInstance()      // 인코딩 하지 않겠다는 조건 noop
    }


//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        super.configure(auth)
//        // 메모리에 유저생성하기
//        auth!!.inMemoryAuthentication()
//                .withUser("joonhee")
//                // `{}` 은 스프링에서 제공하는 인코딩 함수 ; noop -> no option 인코딩하지않는다
//                .password("{noop}1234")
//                .roles("USER")
//                .and()
//                .withUser("{noop}Gong")
//                .password("1234")
//                .roles("ADMIN")
//    }
}