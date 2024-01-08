package org.example.coha.domain.post.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.model.Post
import org.example.coha.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {
    override fun createPost(request: CreatePostRequest): PostResponse {
        TODO("Not yet implemented")
        // 리퀘스트로 받아온 내용을 데이터베이스로 저장하고 PostReponse 로 변환 후에 반환
        postRepository.save(Post(title = request.title, content = request.content, name = request.name).)
    }

    override fun getPostById(postId: Long): String {
        // 받아온 postId에 해당하는 post를 가져와서 PostResponse 로 변환 후 반환
        // 해당 post 의 댓글도 함께 반환
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return "1"
    }
}