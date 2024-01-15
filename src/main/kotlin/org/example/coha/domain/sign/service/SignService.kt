package org.example.coha.domain.sign.service

import org.example.coha.domain.Users.model.User
import org.example.coha.domain.Users.repository.UserRepository
import org.example.coha.domain.exception.DuplicateUsernameException
import org.example.coha.domain.exception.MisMatchedExcpetion
import org.example.coha.domain.security.TokenProvider
import org.example.coha.domain.sign.dto.SignInRequest
import org.example.coha.domain.sign.dto.SignInResponse
import org.example.coha.domain.sign.dto.SignUpRequest
import org.example.coha.domain.sign.dto.SignUpResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val encoder: PasswordEncoder // 암호화해서 저장
) {
    @Transactional

    // 회원가입
    fun registerMember(request: SignUpRequest): SignUpResponse {
        val isExistAccount = userRepository.existsByEmail(request.email)
        if (isExistAccount) throw DuplicateUsernameException(request.email) // 이메일(아이디) 중복 검사

        return SignUpResponse.toSignUpResponse(userRepository.save(User.toUser(request, encoder)))
    }

    // 로그인
    @Transactional
    fun login(request: SignInRequest): SignInResponse {

        val user = userRepository
            .findByEmail(request.email)
            .takeIf { encoder.matches(request.password, it.userPassword) } // .matches() 암호화되지 않은 비밀번호와 암호화되어 저장된 비밀번호를 검사해준다.
            ?: throw MisMatchedExcpetion(request.email)

        val token = tokenProvider.createToken("${user.email}:${user.type}") // 사용자 정보로 토큰 생성
        return SignInResponse(user.email, token)
    }


}