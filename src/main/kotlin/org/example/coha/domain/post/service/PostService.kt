package org.example.coha.domain.post.service

import org.example.coha.domain.post.dto.PostResponse

interface PostService {

    fun getPostById(postId: Long): String
}