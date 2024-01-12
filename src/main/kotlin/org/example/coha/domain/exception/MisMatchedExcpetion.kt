package org.example.coha.domain.exception

data class MisMatchedExcpetion(val email: String):
    RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.") {

}
