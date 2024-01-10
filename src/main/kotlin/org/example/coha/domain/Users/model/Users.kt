package org.example.coha.domain.Users.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class Users(

    @Column(name = "name")
    var name: String,

    @Column(name = "password")
    var userPassword: String,

    @Column(name = "email")
    var email: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}