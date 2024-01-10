package org.example.coha.domain.sign

import org.example.coha.domain.Users.model.User

data class SignUpResponse(
    val id: Long,
    val email: String,
    val name: String,
) {
    companion object {
        fun from(user: User) = SignUpResponse(
            id = user.id!!,
            email = user.email,
            name = user.name,
        )
    }
}
