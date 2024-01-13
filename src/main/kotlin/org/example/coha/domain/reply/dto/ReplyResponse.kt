package org.example.coha.domain.reply.dto

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.reply.model.Reply

class ReplyResponse(
    var id: Long?,
    var author: String,
    var content: String,
    var createdAt: String,
    var cardId: Long
) {
    companion object {
        fun toReplyResponse(reply: Reply): ReplyResponse {
            return ReplyResponse(
                id = reply.id,
                content = reply.content,
                createdAt = reply.createdAt,
                cardId = reply.post.id ?: throw ModelNotFoundException("Post", reply.post.id),
                author = reply.author
            )
        }
    }
}
