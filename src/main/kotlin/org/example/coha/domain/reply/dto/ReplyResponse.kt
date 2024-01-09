package org.example.coha.domain.reply.dto

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.reply.model.Reply

class ReplyResponse(
    var id: Long?,
    var name: String,
    var content: String,
    var createdAt: String,
    var cardId: Long
) {
    companion object {
        fun toReplyResponse(reply: Reply): ReplyResponse {
            return ReplyResponse(
                id = reply.id,
                name = reply.name,
                content = reply.content,
                createdAt = reply.createdAt,
                cardId = reply.post.id ?: throw ModelNotFoundException("Post", reply.post.id)
            )
        }
    }
}
