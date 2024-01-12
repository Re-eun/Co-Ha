package org.example.coha.domain.reply.service

import org.example.coha.domain.reply.dto.CreateReplyRequest
import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest
import org.example.coha.domain.reply.model.Reply

interface ReplyService {


    fun creatReply(createReplyRequest: CreateReplyRequest): ReplyResponse
    fun updateReply(replyId: Long, request: UpdateReplyRequest): ReplyResponse

    fun deleteReply(replyId: Long)

    fun getReplyById(replyId: Long): Reply

}