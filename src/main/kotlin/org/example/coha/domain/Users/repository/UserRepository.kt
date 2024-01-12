package org.example.coha.domain.Users.repository

import org.example.coha.domain.Users.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String) : User
    fun existsByEmail(email: String) : Boolean
}