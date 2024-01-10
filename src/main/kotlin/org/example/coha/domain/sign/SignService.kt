package org.example.coha.domain.sign

import org.example.coha.domain.Users.model.User
import org.example.coha.domain.Users.repository.UserRepository
import org.example.coha.domain.security.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val encoder: PasswordEncoder
) {
    @Transactional
    fun registerMember(request: SignUpRequest): SignUpResponse {
        val isExistAccount = userRepository.existsByEmail(request.email)
        if (isExistAccount) throw IllegalArgumentException("이미 사용중인 아이디입니다.")

        return SignUpResponse.from(userRepository.save(User.toUser(request, encoder)))
    }

    @Transactional
    fun signin(request: SignInRequest): SignInResponse {
//        val isExistAccount = userRepository.existsByEmail(request.email)
//        if (!isExistAccount) throw IllegalArgumentException("존재하지 않는 아이디입니다.")

        val user = userRepository.findByEmail(request.email).takeIf { encoder.matches(request.password, it.userPassword) }
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

        val token = tokenProvider.createToken("${user.id}")
        return SignInResponse(user.name, token)
    }

//    @Transactional
//    fun signIn(request: SignInRequest): SignInResponse {
//        val member = memberRepository.findByAccount(request.account)
//            ?.takeIf { encoder.matches(request.password, it.password) } ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
//        val token = tokenProvider.createToken("${member.id}:${member.type}")
//        return SignInResponse(member.name, member.type, token)
//    }
}