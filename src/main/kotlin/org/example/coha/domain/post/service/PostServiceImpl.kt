package org.example.coha.domain.post.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.model.Post
import org.example.coha.domain.post.model.toResponse
import org.example.coha.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.smartcardio.Card


@Service
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {

    override fun createPost(request: CreatePostRequest): PostResponse {
        val post = postRepository.save(request.toPost())
        return PostResponse.toPostResponse(post)


    }



    override fun getPostById(postId: Long): PostWithReplyResponse {
        // 받아온 postId에 해당하는 post를 가져와서 PostResponse 로 변환 후 반환
        // 해당 post 의 댓글도 함께 반환
        // 해당하는 post가 없을 시 throw ModelNotFoundException
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return PostWithReplyResponse.toPostWithReplyResponse(post)
    }

    override fun updatePost(request: UpdatePostRequest): PostResponse {
        val savedpost = postRepository.save(request.post())

        return PostResponse.toPostResponse(savedpost)


    }
}