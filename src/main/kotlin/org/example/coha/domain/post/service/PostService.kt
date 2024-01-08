package org.example.coha.domain.post.service

import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.UpdatePostRequest


interface PostService {

    fun getPostById(postId: Long): String

    fun updatePost(request: UpdatePostRequest): PostResponse
}