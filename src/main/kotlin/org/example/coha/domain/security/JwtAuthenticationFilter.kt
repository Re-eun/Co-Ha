package org.example.coha.domain.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Order(0) // 의존성 주입 우선순위
@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider
): OncePerRequestFilter() {


    // 헤더에서 토큰 추출
    private fun parseBearerToken(request: HttpServletRequest)
    = request
        .getHeader(HttpHeaders.AUTHORIZATION) // http 요청의 헤더에서 authorizarion 값을 찾아서
        .takeIf { it?.startsWith("Bearer ", true) ?: false } // 접두어 확인, 제거 ( Bearer 타입 )
        ?.substring(7)


    // 토큰으로 정보 추출
    private fun parseUserSpecification(token: String?) = (
            token?.takeIf { it.length >= 10 }
                ?.let { tokenProvider.validateTokenAndGetSubject(it) } // 토큰이 유효한지 확인, 복호화
                ?: "anonymous:anonymous" // 유효하지 않을 때 설정
            ).split(":")
        .let { User(it[0], "", listOf(SimpleGrantedAuthority(it[1]))) } // 사용자 정보 가져와서 스프링 시큐리티 User 객체 생성


    // 인증 정보 설정
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = parseBearerToken(request) // 토큰 추출
        val user = parseUserSpecification(token) // 사용자 정보 추출
        UsernamePasswordAuthenticationToken.authenticated(user, token, user.authorities) // 인증된 사용자를 나타내는 토큰 생성
            .apply { details = WebAuthenticationDetails(request) } // 요청날린 client 또는 프록시의 ip 주소와 세션 id 저장
            .also { SecurityContextHolder.getContext().authentication = it } // sercurityContextHolder 에 인증 정보 저장
        filterChain.doFilter(request, response) // 다음 필터
    }

}