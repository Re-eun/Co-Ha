package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post
import org.springframework.web.multipart.MultipartFile

data class CreatePostRequest(
    val title: String,
    val name: String,
    val content: String,
    val image: MultipartFile? //이미지를 받아올 필드
) {
    fun toPost(imagePath: String?): Post { //request받아온 걸 post로 변환
        return Post(
            title = title,
            name = name,
            content = content,
            imagePath = imagePath
        )
    }
}