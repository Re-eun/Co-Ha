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
        // DB 에 저장된 모든 게시글들을 가져와서 PostResponse로 변환 후 반환
        return postRepository.findAll().map { PostResponse.toPostResponse(it)}

    }

    override fun getPostById(postId: Long): PostWithReplyResponse {
        // 받아온 postId에 해당하는 post를 가져와서 PostResponse 로 변환 후 반환
        // 해당 post 의 댓글도 함께 반환
        // 해당하는 post가 없을 시 throw ModelNotFoundException
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return PostWithReplyResponse.toPostWithReplyResponse(post)
    }

    @Transactional
    override fun updatePost(postId: Long): PostResponse {
        val savedpost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        savedpost.content
        return PostResponse.toPostResponse(savedpost)
    }


    @Transactional
    override fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
    @Transactional
    override fun updateViews(postId: Long) {
        postRepository.updateViews(postId)
    }

}
