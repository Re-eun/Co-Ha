package org.example.coha.domain.reply.controller

import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest
import org.example.coha.domain.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/posts/{postId}/replies")
@RestController
class ReplyController(
    private val replyService: ReplyService
) {



    @PutMapping("/{replyId}")
    fun updateReply(@PathVariable postId: Long,
                    @PathVariable replyId: Long,
                    @RequestBody updateReplyRequest: UpdateReplyRequest
    ): ResponseEntity<ReplyResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(replyService.updateReply(postId, replyId, updateReplyRequest))
    }


}