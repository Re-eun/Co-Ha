package org.example.coha.domain.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests {
                it.requestMatchers("/", "/swagger-ui/**","/v3/**",  "/login", "/signup").permitAll() // 누구나 접근 가능
                    .anyRequest().authenticated() // 이외의 경로는 인증 필요
            }
            .formLogin { formLogin: FormLoginConfigurer<HttpSecurity?> ->
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/posts") // 로그인 완료시 경로
            }
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제 여부
            }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() } // 공격 방지위해서는 활성화해야 함
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }    // 세션을 사용하지 않으므로 STATELESS 설정
            .build()
    }
}