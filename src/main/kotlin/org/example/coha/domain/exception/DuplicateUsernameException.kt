package org.example.coha.domain.exception

data class DuplicateUsernameException(val email: String):
    RuntimeException("이미 존재하는 아이디입니다. email: ${email}") {

}
