package org.example.coha.domain.post.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.exception.UnauthorizedAccess
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.model.Post
import org.example.coha.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val fileStorageService: FileStorageService
): PostService {

    @Transactional
    override fun createPost(request: CreatePostRequest, image:MultipartFile?): PostResponse {
        val currentUser = SecurityContextHolder.getContext().authentication.name
        val url = if(image != null) fileStorageService.storeFile(image) else ""

        //이미지 저장 및 경로 획득
        //val imagePath = request.image?.let {fileStorageService.storeFile(it)}

        //Post 객체 생성 및 저장
        val post = postRepository.save(request.toPost(url, currentUser))
        return PostResponse.toPostResponse(post)


    }

    override fun getAllPostList(): List<PostResponse> {
        return postRepository.findAll().map { PostResponse.toPostResponse(it) }

    }

    override fun getPostById(postId: Long): PostWithReplyResponse {

        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return PostWithReplyResponse.toPostWithReplyResponse(post)
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val savedPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val currentUser = SecurityContextHolder.getContext().authentication.name
        if (savedPost.author != currentUser) throw UnauthorizedAccess()
        savedPost.updatePost(request)
        return PostResponse.toPostResponse(savedPost)

    }


    @Transactional
    override fun deletePost(postId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val currentUser = SecurityContextHolder.getContext().authentication.name
        if (post.author != currentUser) throw UnauthorizedAccess()
        postRepository.deleteById(postId)
    }

    @Transactional
    override fun updateViews(postId: Long) {
        postRepository.updateViews(postId)
    }

}






