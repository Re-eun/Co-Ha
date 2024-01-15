package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post
import org.springframework.web.multipart.MultipartFile

data class CreatePostRequest(
        val title: String,
        val content: String

) {

    fun toPost(imagePath: String, currentUser: String): Post {

        return Post(
                title = title,
                content = content,
                author = currentUser,
                view = 0,
                imagePath = imagePath

        )
    }
}