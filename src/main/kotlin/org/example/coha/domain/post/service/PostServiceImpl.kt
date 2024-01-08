package org.example.coha.domain.post.service

import org.example.coha.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.springframework.data.repository.findByIdOrNull



@Service
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {

    override fun getPostById(postId: Long): String {
        // 받아온 postId에 해당하는 post를 가져와서 PostResponse 로 변환 후 반환
        // 해당 post 의 댓글도 함께 반환
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return "1"
    }

    override fun updatePost(request: UpdatePostRequest): PostResponse {
        val savedpost = postRepository.save(request.post())

        return PostResponse.toPostResponse(savedpost)


    }
}