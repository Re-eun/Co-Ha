package org.example.coha.domain.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableMethodSecurity // 메소드 시큐리티 활성화
@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/v3/**", "/signup", "/login", "/error")

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() } // 공격 방지위해서는 활성화해야 함
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()	// requestMatchers의 인자로 전달된 url을 모두에게 허용
                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }	// 세션을 사용하지 않으므로 STATELESS 설정
        .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java) // 필터(인증)
        .build()!!



    // 패스워드 인코더 등록
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder =
        BCryptPasswordEncoder() // 해시 함수를 사용하여 패스워드를 저장하고 검증하는데 사용되는 PasswordEncoder 의 구현체




}