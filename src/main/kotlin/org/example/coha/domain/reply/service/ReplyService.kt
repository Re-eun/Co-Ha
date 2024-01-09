package org.example.coha.domain.reply.service

import org.example.coha.domain.reply.dto.CreateReplyRequest
import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest

interface ReplyService {


    fun creatReply(createReplyRequest: CreateReplyRequest): ReplyResponse
    fun updateReply(postId: Long, replyId: Long, request: UpdateReplyRequest): ReplyResponse

    fun deleteReply(postId: Long, replyId: Long)
}