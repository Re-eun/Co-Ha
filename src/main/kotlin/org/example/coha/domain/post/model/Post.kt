package org.example.coha.domain.post.model

import jakarta.persistence.*
import org.example.coha.domain.reply.model.Reply
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "post")
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "name")
    var name: String,

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val replies: MutableList<Reply> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    var post: Post
) {

    @Column(name = "created_at")
    var createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH-mm"))
}
