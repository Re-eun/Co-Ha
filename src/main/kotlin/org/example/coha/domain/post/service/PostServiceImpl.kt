package org.example.coha.domain.post.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.model.Post
import org.example.coha.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val fileStorageService: FileStorageService
): PostService {

    // 이미지 업로드 및 게시글 생성
    override fun createPost(request: CreatePostRequest, image: MultipartFile?): PostResponse {
        //이미지 저장 및 경로 획득
        //val imagePath = request.image?.let {fileStorageService.storeFile(it)}

        val url = if( image != null) fileStorageService.storeFile(image) else ""

        //Post 객체 생성 및 저장
        val post = postRepository.save(request.toPost(url))
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
    override fun updatePost(postId: UpdatePostRequest): PostResponse {
        val savedpost = postRepository.findByIdOrNull(postId.id) ?: throw ModelNotFoundException("Post", postId.id)
        savedpost.content = postId.content
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

    //CreatePostRequest 확장함수에 이미지 처리 로직 추가
    private fun CreatePostRequest.toPost(imagePath: String?): Post {
        return Post(
                title = this.title,
                name = this.name,
                content = this.content,
                view = 0,
                imagePath = imagePath
        )
    }

}
