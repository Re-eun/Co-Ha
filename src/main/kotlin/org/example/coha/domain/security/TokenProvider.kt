package org.example.coha.domain.security

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@PropertySource("classpath:jwt.yml") // 설정 파일 경로
@Service
class TokenProvider(
    @Value("\${secret-key}")
    private val secretKey: String,
    @Value("\${expiration-hours}")
    private val expirationHours: Long,
    @Value("\${issuer}")
    private val issuer: String
) {
    // user 고유 정보로 토큰 생성
    fun createToken(userSpecification: String) = Jwts.builder()
        .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.toByteArray()))
        .setSubject(userSpecification) // 고유 정보로 주체 설정
        .setIssuer(issuer) // 발급자
        .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // 발급 시간
        .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS))) // 토큰 만료 설정
        .compact()!!


    // 토큰의 subject 를 복호화하여 문자열 형태로 반환 ( 유효한지 확인, 유효하지 않을 경우 null )
    fun validateTokenAndGetSubject(token: String): String? = Jwts.parserBuilder()
        .setSigningKey(secretKey.toByteArray()) // 비밀키로 복호화
        .build()
        .parseClaimsJws(token) // 파싱 : jwt 형식에 맞게 데이터를 해석하고 추출
        .body // 토큰의 본문 (claims) 에 접근
        .subject // 주체 반환


}