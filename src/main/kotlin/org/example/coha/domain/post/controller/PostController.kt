package org.example.coha.domain.post.controller

import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/posts")
@RestController
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(
        @RequestBody createPostRequest: CreatePostRequest
    ): ResponseEntity<PostResponse> {
    return  ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest))
    }


    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest,
    ): ResponseEntity<PostResponse>{

        val post: PostResponse = postService.updatePost(postId)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(post)

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


    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<String> {
        postService.deletePost(postId)
        val deletePostSuccessMessage = "게시글이 성공적으로 삭제되었습니다."

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletePostSuccessMessage)
    }



}


