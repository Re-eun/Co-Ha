package org.example.coha.domain.post.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.example.coha.domain.common.SortOrder
import org.example.coha.domain.post.dto.CreatePostRequest
import org.example.coha.domain.post.dto.PostResponse
import org.example.coha.domain.post.dto.PostWithReplyResponse
import org.example.coha.domain.post.dto.UpdatePostRequest
import org.example.coha.domain.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@Tag(name = "게시판")
@RequestMapping("/posts")
@RestController
class PostController(
        private val postService: PostService
) {

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPost(
            @RequestPart data: CreatePostRequest,
            @RequestPart("image") image: MultipartFile?
    ): ResponseEntity<Any> {
        if (image == null || image.isEmpty) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지를 넣어주세요")
        }

        val imageUrl = postService.storesupaFile(image)
        postService.createPost(data, imageUrl)

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 생성되었습니다.")
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
    fun getAllPostList(@RequestParam sortOrder: SortOrder): ResponseEntity<List<PostResponse>> {

        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostList(sortOrder))
    }

    @GetMapping("/{postId}")
    fun getPostById(
            @PathVariable postId: Long
    ): ResponseEntity<PostWithReplyResponse> {
        postService.updateViews(postId)
        val updatePost = postService.getPostById(postId)
        return ResponseEntity.status(HttpStatus.OK).body(updatePost)

    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<String> {

        // postService를 사용하여 주어진 postId에 해당하는 게시글을 삭제하는 메소드 호출
        postService.deletePost(postId)
        // 게시글 삭제가 성공하면 "게시글이 성공적으로 삭제되었습니다."라는 메세지 생성
        val deletePostSuccessMessage = "게시글이 성공적으로 삭제되었습니다."

        //ResponseEntity를 사용해서 HTTP 응답을 생성
        //HttpStatus.OK는 HTTP 상태코드 200을 나타냄
        //body에는 삭제 성공 메세지를 담아 클라이언트에게 보여줌
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletePostSuccessMessage)
    }




}