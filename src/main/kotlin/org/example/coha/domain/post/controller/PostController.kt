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
        @RequestBody postRequest: UpdatePostRequest,
    ): ResponseEntity<PostResponse>{
        val request = UpdatePostRequest(
            id = postId,
            title = postRequest.title,
            content = postRequest.content,
            name = postRequest.name

        )
        val post: PostResponse = postService.updatePost(request)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(post)

    }


    @GetMapping("/{postId}")
    fun getPostById(
        @PathVariable postId: Long
    ): ResponseEntity<PostWithReplyResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId))

    }


    @DeleteMapping("/{postId")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Unit> {
        postService.deletePost(postId)
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build()
    }




}