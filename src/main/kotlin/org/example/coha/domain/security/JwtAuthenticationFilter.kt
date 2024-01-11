package org.example.coha.domain.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Order(0) // 의존성 주입 우선순위
@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider
): OncePerRequestFilter() {

    // 인증 정보 설정
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = parseBearerToken(request)
        val user = parseUserSpecification(token)
        UsernamePasswordAuthenticationToken.authenticated(user, token, user.authorities)
            .apply { details = WebAuthenticationDetails(request) }
            .also { SecurityContextHolder.getContext().authentication = it }
        filterChain.doFilter(request, response)
        }

    private fun parseBearerToken(request: HttpServletRequest)
    = request
        .getHeader(HttpHeaders.AUTHORIZATION) // http 요청의 헤더에서 authorizarion 값을 찾아서
        .takeIf { it?.startsWith("Bearer ", true) ?: false } // 접두어 확인, 제외하고 파싱
        ?.substring(7)

    private fun parseUserSpecification(token: String?) = (
            token?.takeIf { it.length >= 10 }
                ?.let { tokenProvider.validateTokenAndGetSubject(it) }
                ?: "anonymous:anonymous"
            ).split(":")
        .let { User(it[0], "", listOf(SimpleGrantedAuthority(it[1]))) }
}