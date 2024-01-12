package org.example.coha.domain.post.service

import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.model.Post
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable


interface PostService {

    fun createPost(request: CreatePostRequest): PostResponse

    fun getAllPostList(): List<PostResponse>

    fun getPostById(postId: Long): PostWithReplyResponse


    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse


    fun deletePost(postId: Long)

    fun updateViews(postId: Long)


}

