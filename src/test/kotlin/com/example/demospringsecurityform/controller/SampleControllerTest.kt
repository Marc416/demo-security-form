package com.example.demospringsecurityform.controller

import com.example.demospringsecurityform.account.domain.entity.Account
import com.example.demospringsecurityform.account.domain.service.AccountService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

// mockmvc가 빈주입을 받지못하는 이유
// (https://stackoverflow.com/questions/66321376/why-my-org-springframework-test-web-servlet-mockmvc-framework-is-failing-to-reso)
@Suppress("NonAsciiCharacters")
@AutoConfigureMockMvc
@SpringBootTest
internal class SampleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var accountService: AccountService

    @Test
    fun `anonymous 로 인덱스 접근`() {
        mockMvc.perform(get("/").with(anonymous()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
    }

    @Test
    @WithAnonymousUser
    fun `어노테이션사용시-anonymous 로 인덱스 접근`() {
        mockMvc.perform(get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
    }

    @Test
    fun `이미 로그인한 유저가 인덱스로 접근`() {
        mockMvc.perform(get("/").with(user("joonhee").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
    }

    @Test
    @WithUserJoonHeeAnnotation      // 커스텀 어노테이션으로 중복된 로그인된 유저코드를 줄일 수 있음
    fun `어노테이션사용시-이미 로그인한 유저가 인덱스로 접근`() {
        mockMvc.perform(get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
    }

    @Test
    fun `Admin 페이지에 유저가 접근할 때`() {
        mockMvc.perform(get("/admin").with(user("joonhee").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUser(username = "joonhee", roles = ["ADMIN"])
    fun `어노테이션사용-Admin 페이지에 어드민이 접근할 때`() {
        mockMvc.perform(get("/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    @Transactional      // Transactional 은 테스트후 db 를 원복 시키는 기능도 있 이라고하는데 어떻게..?
    fun `로그인폼 테스트`() {
        // Arrange
        val username = "joonhee"
        val password = "1234"
        val role = "USER"
        val user = Account(username = username, password = password, role = role)

        accountService.create(user)

        // Action, Assert
        mockMvc.perform(
            SecurityMockMvcRequestBuilders.formLogin()
                    .user(username)
                    .password(password)
        )
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
    }

    @Test
    @Transactional
    fun `비밀번호가 다른겨우 로그인폼에 로그인 불가`() {
        // Arrange
        val username = "joonhee"
        val password = "1234"
        val role = "USER"
        val user = Account(username = username, password = password, role = role)

        accountService.create(user)

        // Action, Assert
        mockMvc.perform(
            SecurityMockMvcRequestBuilders.formLogin()
                    .user(username)
                    .password("12345")
        )
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
    }
}