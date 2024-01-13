package org.example.coha.domain.post.controller

import io.swagger.v3.oas.annotations.tags.Tag
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
       @RequestPart data: CreatePostRequest, // 게시물 생성에 필요한 데이터를 나타내는 객체
       @RequestPart("image") image: MultipartFile? //게시물에 첨부될 이미지 파일
    ): ResponseEntity<Boolean> {

        // postservice를 사용하여 게시물을 생성하는 메소드를 호출함
        // 해당 메소드는 data와 image를 파라미터로 받아서 게시물을 생성하고 저장할 수 있음
        postService.createPost(data,image)

        //ResponseEntity를 사용해서 HTTP 응답을 생성
        //Http.Status.CREATED 는 HTTP 상태코드 201 Created를 나타냄
        //body에는 true를 담아 클라이언트에게 성공적으로 처리되었음을 보여줌
        return  ResponseEntity.status(HttpStatus.CREATED).body(true)
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


