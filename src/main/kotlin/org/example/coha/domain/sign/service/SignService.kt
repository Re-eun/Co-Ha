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
    private val encoder: PasswordEncoder
) {
    @Transactional
    fun registerMember(request: SignUpRequest): SignUpResponse {
        val isExistAccount = userRepository.existsByEmail(request.email)
        if (isExistAccount) throw DuplicateUsernameException(request.email)

        return SignUpResponse.toSignUpResponse(userRepository.save(User.toUser(request, encoder)))
    }

    @Transactional
    fun signin(request: SignInRequest): SignInResponse {

        val user = userRepository.findByEmail(request.email).takeIf { encoder.matches(request.password, it.userPassword) }
            ?: throw MisMatchedExcpetion(request.email)

        val token = tokenProvider.createToken("${user.email}:${user.type}")
        return SignInResponse(user.email, token)
    }


}