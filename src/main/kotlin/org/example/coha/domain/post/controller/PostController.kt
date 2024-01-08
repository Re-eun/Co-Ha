package org.example.coha.domain.post.controller

import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/posts")
@RestController
class PostController{

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody postRequest: UpdatePostRequest,
    ): ResponseEntity<PostResponse>{
        val request = UpdatePostRequest(
            id = postId,
            title = postRequest.title,
            content = postRequest.content,
            createdAt = postRequest.createdAt,
            name = postRequest.name

        )
        val post: PostResponse = PostService.updatePost(request)

        return ResponseEntity
            .status(HttpStatuis.OK)
            .body(todoCard)

    }
}