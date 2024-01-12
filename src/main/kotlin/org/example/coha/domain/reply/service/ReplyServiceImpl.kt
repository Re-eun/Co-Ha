package org.example.coha.domain.reply.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.exception.UnauthorizedAccess
import org.example.coha.domain.post.repository.PostRepository
import org.example.coha.domain.reply.dto.CreateReplyRequest
import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest
import org.example.coha.domain.reply.model.Reply
import org.example.coha.domain.reply.repository.ReplyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val postRepository: PostRepository
): ReplyService {

    // 댓글 작성
    override fun creatReply(createReplyRequest: CreateReplyRequest): ReplyResponse{
        val targetPost = postRepository.findByIdOrNull(createReplyRequest.postId) ?: throw ModelNotFoundException("Post", createReplyRequest.postId)

        val currentUser = SecurityContextHolder.getContext().authentication.name

        val reply = Reply(
            name = createReplyRequest.name,
            content = createReplyRequest.content,
            post = targetPost,
            createdAt = createReplyRequest.createdAt,
            author = currentUser
        )

        val result = replyRepository.save(reply)

        return ReplyResponse.toReplyResponse(result)
    }


    // 댓글 수정
    @Transactional
    override fun updateReply(postId: Long, replyId: Long, request: UpdateReplyRequest): ReplyResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)
        val currentUser = SecurityContextHolder.getContext().authentication.name
        if(reply.author != currentUser) throw UnauthorizedAccess()
        reply.content = request.content

        return ReplyResponse.toReplyResponse(reply)
    }


    // 댓글 삭제
    @Transactional
    override fun deleteReply(postId: Long, replyId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)
        val currentUser = SecurityContextHolder.getContext().authentication.name
        if(reply.author != currentUser) throw UnauthorizedAccess()
        replyRepository.delete(reply)
    }


    override fun getReplyById(replyId: Long): Reply {
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)
        return reply
    }
}