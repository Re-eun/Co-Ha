package org.example.coha.domain.post.service

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.runBlocking
import org.example.coha.domain.common.SortOrder
import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.exception.UnauthorizedAccess
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.repository.PostRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.time.Duration.Companion.seconds


@Service
class PostServiceImpl(
        private val postRepository: PostRepository, //게시글 저장소(PostRepository) 주입
        @Value("\${supabase.url}") private val supabaseUrl: String,
        @Value("\${supabase.key}") private val supabaseKey: String
): PostService {

    @Transactional
    override fun createPost(request: CreatePostRequest, imageUrl: String): PostResponse {
        val currentUser = SecurityContextHolder.getContext().authentication.name

        // Post 객체 생성 및 저장
        val post = postRepository.save(request.toPost(imageUrl, currentUser))
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


    override fun storesupaFile(file: MultipartFile): String {

        val supabaseClient = createSupabaseClient(
                supabaseUrl = supabaseUrl,
                supabaseKey = supabaseKey
        ) {
            install(Storage) {
                transferTimeout = 90.seconds
            }
        }
        val bucket = supabaseClient.storage.from("coha")
        val filePath = "${UUID.randomUUID()}.png"
        runBlocking {
            bucket.upload(filePath, file.bytes, upsert = false)
        }

        return supabaseClient.storage.from("coha").publicUrl(filePath)
    }
}