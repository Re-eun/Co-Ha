package org.example.coha.domain.sign.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.example.coha.domain.sign.dto.SignInRequest
import org.example.coha.domain.sign.dto.SignUpRequest
import org.example.coha.domain.sign.service.SignService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 가입 및 로그인")
@RestController
@RequestMapping
class SignController(
    private val signService: SignService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest) =
        signService.registerMember(request)

    @PostMapping("/login")
    fun login(@RequestBody request: SignInRequest) =
        signService.signin(request)


//    @Operation(summary = "로그인")
//    @PostMapping("/sign-in")
//    fun signIn(@RequestBody request: SignInRequest) = ApiResponse.success(signService.signIn(request))
}