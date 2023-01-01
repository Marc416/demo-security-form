package com.example.demospringsecurityform.controller

import org.springframework.security.test.context.support.WithMockUser

@Retention(AnnotationRetention.RUNTIME)
@WithMockUser(username = "joonhee", roles = ["USER"])
annotation class WithUserJoonHeeAnnotation {

}