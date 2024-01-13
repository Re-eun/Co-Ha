package org.example.coha.domain.post.service

import org.example.coha.domain.common.SortOrder
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
    private val postRepository: PostRepository, //게시글 저장소(PostRepository) 주입
    private val fileStorageService: FileStorageService //파일 저장 및 로드 서비스(FiletorageService) 주입
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

    override fun getAllPostList(sortOrder: SortOrder): List<PostResponse> {
        var postList: List<PostResponse> = listOf()
        if (sortOrder == SortOrder.DESC) {
            postList = postRepository.findAllByOrderByCreatedAtDesc()
        } else {
            postList = postRepository.findAllByOrderByCreatedAtAsc()
        }

        return postList

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

        //현재 사용자와 게시글 작성자가 다를 경우 삭제 권한이 없음
        if (post.author != currentUser) throw UnauthorizedAccess()
        //게시글 삭제
        postRepository.deleteById(postId)
    }

    @Transactional
    override fun updateViews(postId: Long) {
        postRepository.updateViews(postId)// postRepository에 updateViews 기능을 활성화
    }

}






