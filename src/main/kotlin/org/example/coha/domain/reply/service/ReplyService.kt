package org.example.coha.domain.reply.service

import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest

interface ReplyService {


    fun updateReply(postId: Long, replyId: Long, request: UpdateReplyRequest): ReplyResponse
}