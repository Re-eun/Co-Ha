package org.example.coha.domain.reply.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
    var post: Post

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}