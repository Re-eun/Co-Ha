package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post
import org.springframework.web.multipart.MultipartFile

// 게시글 생성 요청에 사용되는 데이터 클래스인 CreatePostRequest 정의
data class CreatePostRequest(
        val title: String,
        val content: String,
        val imageUrl: String? = "https://localhost:8080/image.jpg"

) {
    fun toPost(url: String, currentUser: String): Post {
        //post로 객체를 생성하고 반환
        return Post(
                title = title,
                content = content,
                author = currentUser,
                view = 0,
                imagePath = imageUrl

        )
    }
}