package org.example.coha.domain.post.model

import jakarta.persistence.*
import org.example.coha.domain.reply.model.Reply

@Entity
@Table(name = "post")
class Post(

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "created_at")
    var createdAt: String,

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true )
    val replies: MutableList<Reply> = mutableListOf()
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}