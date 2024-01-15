package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post
import org.example.coha.domain.reply.dto.ReplyResponse

class PostWithReplyResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
    val view: Int,
    val replies: List<ReplyResponse>

    ) {
    companion object {
        fun toPostWithReplyResponse(post: Post): PostWithReplyResponse {
            return PostWithReplyResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                author = post.author,
                createdAt = post.createdAt,
                view = post.view,
                replies = post.replies.map { ReplyResponse.toReplyResponse(it) },

            )
        }
    }
}