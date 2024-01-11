package org.example.coha.domain.reply.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.repository.PostRepository
import org.example.coha.domain.reply.dto.CreateReplyRequest
import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest
import org.example.coha.domain.reply.model.Reply
import org.example.coha.domain.reply.repository.ReplyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val postRepository: PostRepository
): ReplyService {

    @Transactional

    override fun creatReply(createReplyRequest: CreateReplyRequest): ReplyResponse{
        val targetPost = postRepository.findByIdOrNull(createReplyRequest.postId)
            ?: throw Exception("target post is not found")

        val reply = Reply(
            name = createReplyRequest.name,
            content = createReplyRequest.content,
            post = targetPost,
            createdAt = createReplyRequest.createdAt

        )

        val result = replyRepository.save(reply)

        return ReplyResponse.toReplyResponse(result)
    }


    @Transactional
    override fun updateReply(postId: Long, replyId: Long, request: UpdateReplyRequest): ReplyResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)


        reply.content = request.content

        return ReplyResponse.toReplyResponse(reply)
    }




    @Transactional
    override fun deleteReply(postId: Long, replyId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)
        replyRepository.delete(reply)
    }

}