package org.example.coha.domain.Users.model

import jakarta.persistence.*
import org.example.coha.domain.common.UserType
import org.example.coha.domain.sign.dto.SignUpRequest
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "users")
class User(

    @Column(name = "name")
    var name: String,

    @Column(name = "password")
    var userPassword: String,

    @Column(name = "email")
    var email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: UserType = UserType.USER,



) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun toUser(request: SignUpRequest, encoder: PasswordEncoder) = User(
            email = request.email,
            userPassword = encoder.encode(request.userPassword),
            name = request.name,

        )
    }

//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        return list.Of(SimpleGrantedAuthority("USER"))
//    }
//
//    override fun getPassword(): String {
//        return userPassword
//    }
//
//    override fun getUsername(): String {
//        return email
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return true
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isEnabled(): Boolean {
//        return true
//    }
}