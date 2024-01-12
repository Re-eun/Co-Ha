package org.example.coha.domain.reply.model

import jakarta.persistence.*
import org.example.coha.domain.post.model.Post

@Entity
@Table(name = "reply")
class Reply(
    @Column(name = "name")
    var name: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "created_at")
    var createdAt: String,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post,

//    @Column(name = "author")
//    var author: String

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}