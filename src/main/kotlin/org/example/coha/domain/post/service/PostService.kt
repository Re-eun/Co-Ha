package org.example.coha.domain.post.service

import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse


interface PostService {

    fun createPost(request: CreatePostRequest): PostResponse

    fun getAllPostList(): List<PostResponse>

    fun getPostById(postId: Long): PostWithReplyResponse

    fun updatePost(postId: Long): PostResponse

    fun deletePost(postId: Long)

    fun updateViews(postId: Long)


}

