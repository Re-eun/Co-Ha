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

    @Column(name = "image_path") // 이미지 경로 추가
    var imagePath: String? = null,

    @Column(name = "view")
    var view: Int,

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val replies: MutableList<Reply> = mutableListOf()
) {

    @Column(name = "created_at")
    var createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH-mm"))
}
