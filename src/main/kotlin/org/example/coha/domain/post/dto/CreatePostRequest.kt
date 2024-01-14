package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post
import org.example.coha.domain.post.service.FileStorageService
import org.springframework.web.multipart.MultipartFile

data class CreatePostRequest(
        val title: String,
        val content: String,
        val imageUrl: MultipartFile? = null

) {
    fun toPost(url: String, currentUser: String): Post {


        //post로 객체를 생성하고 반환
        return Post(
                title = title,
                content = content,
                author = currentUser,
                view = 0,
                imagePath = url

        )
    }
}