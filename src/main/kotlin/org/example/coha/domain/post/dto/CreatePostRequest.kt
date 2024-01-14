package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post
import org.example.coha.domain.post.service.FileStorageService
import org.springframework.web.multipart.MultipartFile

data class CreatePostRequest(
        val title: String,
        val content: String,
        val url: MultipartFile? = null

) {
    fun toPost(url: String, currentUser: String): Post {
        return Post(
                title = title,
                content = content,
                author = currentUser,
                view = 0,
                imagePath = url

        )
    }
}