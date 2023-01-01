package com.example.demospringsecurityform.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

// mockmvc가 빈주입을 받지못하는 이유
// (https://stackoverflow.com/questions/66321376/why-my-org-springframework-test-web-servlet-mockmvc-framework-is-failing-to-reso)
@Suppress("NonAsciiCharacters")
@AutoConfigureMockMvc
@SpringBootTest
internal class SampleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

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
}