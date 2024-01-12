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
        @RequestBody createReplyRequest:    CreateReplyRequest,
        principal: Principal
    ): ResponseEntity<ReplyResponse>{
        val result = replyService.creatReply(createReplyRequest)

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

        replyService.deleteReply(replyId)

        val deleteReplySuccessMessage = "댓글이 성공적으로 삭제되었습니다."

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deleteReplySuccessMessage)
    }


}