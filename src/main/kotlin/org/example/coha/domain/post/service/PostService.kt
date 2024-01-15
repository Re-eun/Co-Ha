package org.example.coha.domain.post.service

import org.example.coha.domain.common.SortOrder
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.model.Post
import org.springframework.web.multipart.MultipartFile


interface PostService {

    fun createPost(request: CreatePostRequest, imageUrl: String): PostResponse

    fun getAllPostList(sortOrder: SortOrder): List<PostResponse>

    fun getPostById(postId: Long): PostWithReplyResponse

    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse

    fun deletePost(postId: Long)

    fun updateViews(postId: Long)

    fun storesupaFile(file:MultipartFile): String



}

