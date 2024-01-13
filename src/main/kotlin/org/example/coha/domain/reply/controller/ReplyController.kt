package org.example.coha.domain.reply.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.example.coha.domain.reply.dto.CreateReplyRequest
import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest
import org.example.coha.domain.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Tag(name = "댓글 관리")
@RequestMapping("/posts/{postId}/replies")
@RestController
class ReplyController(
        private val replyService: ReplyService
) {

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    fun creatReply(

            //요청 본문에 있는 데이터를 나타내는 createReplyRequest 객체를 받음
            @RequestBody createReplyRequest: CreateReplyRequest,
            //현재 인증된 사용자 정보를 나타내는 Principal 객체를 받음
            principal: Principal
    ): ResponseEntity<ReplyResponse> {
        // ReplyService를 사용하여 댓글 생성 메소드를 호출하고 결과값을 받음
        val result = replyService.creatReply(createReplyRequest)

        //HTTP 응답을 생성하여 클라이언트에게 결과를 반환함
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(replyService.creatReply(createReplyRequest))
    }


    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{replyId}")
    fun updateReply(@PathVariable replyId: Long,
                    @RequestBody updateReplyRequest: UpdateReplyRequest
    ): ResponseEntity<ReplyResponse> {

        val updateReply = replyService.updateReply(replyId, updateReplyRequest)

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateReply)
    }


    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{replyId}")

    fun deleteReply(
            @PathVariable replyId: Long
    ): ResponseEntity<String> {

        //ReplyService를 사용하여 주어진 댓글 ID에 해당하는 댓글을 삭제
        replyService.deleteReply(replyId)
        // 댓글 삭제를 성공했을 때 알리는 메세지
        val deleteReplySuccessMessage = "댓글이 성공적으로 삭제되었습니다."

        //HTTP 응답을 생성하여 클라이언트에게 결과 메세지를 반환
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deleteReplySuccessMessage)
    }


}