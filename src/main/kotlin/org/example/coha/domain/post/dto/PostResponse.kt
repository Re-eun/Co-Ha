package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post

data class PostResponse(
    var id: Long?,
    var title: String,
    var content: String,
    var imagePath: String?,
    var author: String,
    var createdAt: String,
    var view: Int
) {
    companion object {
        fun toPostResponse(post: Post): PostResponse { //post객체를 postresponse로 변환
            return PostResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                imagePath = post.imagePath,
                author = post.author,
                createdAt = post.createdAt,
                view = post.view
            )
        }
    }
}
