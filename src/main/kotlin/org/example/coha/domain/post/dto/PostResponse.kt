package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post

data class PostResponse(
    var id: Long?,
    var title: String,
    var content: String,
    var name: String,
    var createdAt: String
) {
    companion object {
        fun toPostResponse(post: Post): PostResponse { //post객체를 postresponse로 변환
            return PostResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                name = post.name,
                createdAt = post.createdAt.toString()
            )
        }
    }
}
