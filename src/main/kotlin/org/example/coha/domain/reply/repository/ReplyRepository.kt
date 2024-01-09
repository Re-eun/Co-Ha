package org.example.coha.domain.reply.repository

import org.example.coha.domain.reply.model.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository: JpaRepository<Reply, Long> {
}