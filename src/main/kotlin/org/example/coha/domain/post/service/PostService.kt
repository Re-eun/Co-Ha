package org.example.coha.domain.post.service


interface PostService {

    fun getPostById(postId: Long): String
}