package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post

data class CreatePostRequest(
    val title: String,
    val name: String,
    val content: String
) {
    fun toPost(): Post {
        return Post(
            title = title,
            name = name,
            content = content
        )
    }
}