package org.example.coha.domain.sign

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 가입 및 로그인 API")
@RestController
@RequestMapping
class SignController(
    private val signService: SignService
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request:SignUpRequest) =
        signService.registerMember(request)

    @PostMapping("/sign-in")
    fun login(@RequestBody request: SignInRequest) =
        signService.signin(request)


//    @Operation(summary = "로그인")
//    @PostMapping("/sign-in")
//    fun signIn(@RequestBody request: SignInRequest) = ApiResponse.success(signService.signIn(request))
}