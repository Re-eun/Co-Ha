package org.example.coha.domain.post.service

import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse


interface PostService {

    fun createPost(request: CreatePostRequest): PostResponse

    fun getPostById(postId: Long): PostWithReplyResponse
}