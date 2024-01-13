package org.example.coha.domain.reply.model

import jakarta.persistence.*
import org.example.coha.domain.post.model.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "reply")
class Reply(

    @Column(name = "content")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

    @Column(name = "author")
    var author: String

) {
    @Column(name = "created_at")
    var createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH-mm"))

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}