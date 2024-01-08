package org.example.coha.domain.post.controller

import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

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
    ): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId))

    }
}