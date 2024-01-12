package org.example.coha.domain.sign.dto

import org.example.coha.domain.Users.model.User

data class SignUpResponse(
    val id: Long,
    val email: String,
    val name: String,
) {
    companion object {
        fun toSignUpResponse(user: User) = SignUpResponse(
            id = user.id!!,
            email = user.email,
            name = user.name,
        )
    }
}
