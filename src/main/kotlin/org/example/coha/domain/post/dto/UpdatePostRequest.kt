package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post

data class UpdatePostRequest(
    val id: Long?,
    val title: String,
    val content: String,
    val name: String,
) {
    fun to(): Post {
        return  Post(
            id = id,
            title = title,
            content = content,
            name = name
        )
    }
}