package org.example.coha.domain.post.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {

    override fun createPost(request: CreatePostRequest): PostResponse {
        val post = postRepository.save(request.toPost())
        return PostResponse.toPostResponse(post)

    }

    override fun getAllPostList(): List<PostResponse> {
        return postRepository.findAll().map { PostResponse.toPostResponse(it)}

    }

    override fun getPostById(postId: Long): PostWithReplyResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return PostWithReplyResponse.toPostWithReplyResponse(post)
    }

    @Transactional
    override fun updatePost(request: UpdatePostRequest): PostResponse {
        val savedpost = postRepository.findByIdOrNull(request.id) ?: throw ModelNotFoundException("Post", request.id)
        savedpost.content = request.content
        return PostResponse.toPostResponse(savedpost)
    }


    @Transactional
    override fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }

}