package org.example.coha.domain.post.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@Tag(name = "게시판")
@RequestMapping("/posts")
@RestController
class PostController(
    private val postService: PostService
) {

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    fun createPost(
        @RequestBody createPostRequest: CreatePostRequest,
    ): ResponseEntity<PostResponse> {
    return  ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postService.createPost(createPostRequest))
    }



    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest,
    ): ResponseEntity<PostResponse>{

        val savePost: PostResponse = postService.updatePost(postId, updatePostRequest)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(savePost)
    }


    @GetMapping
    fun getAllPostList(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostList())
    }

    @GetMapping("/{postId}")
    fun getPostById(
        @PathVariable postId: Long
    ): ResponseEntity<PostWithReplyResponse> {
        postService.updateViews(postId)
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId))

    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<String> {

        postService.deletePost(postId)
        val deletePostSuccessMessage = "게시글이 성공적으로 삭제되었습니다."

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletePostSuccessMessage)
    }



}


