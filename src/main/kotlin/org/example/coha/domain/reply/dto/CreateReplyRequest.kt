package org.example.coha.domain.reply.dto

data class CreateReplyRequest(
    val name: String,
    val content: String,
    val postId: Long,
    val createdAt: String
)
