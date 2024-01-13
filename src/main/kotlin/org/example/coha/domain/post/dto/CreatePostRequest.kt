package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post

data class CreatePostRequest(
    val title: String,
    val content: String

) {
    fun toPost(userName: String): Post { //request받아온 걸 post로 변환
        return Post(
            title = title,
            content = content,
            author = userName,
            view = 0

        )
    }
}