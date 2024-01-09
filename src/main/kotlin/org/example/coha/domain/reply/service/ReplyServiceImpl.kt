package org.example.coha.domain.reply.service

import org.example.coha.domain.exception.ModelNotFoundException
import org.example.coha.domain.post.repository.PostRepository
import org.example.coha.domain.reply.dto.ReplyResponse
import org.example.coha.domain.reply.dto.UpdateReplyRequest
import org.example.coha.domain.reply.repository.ReplyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val postRepository: PostRepository
): ReplyService {

    override fun updateReply(postId: Long, replyId: Long, request: UpdateReplyRequest): ReplyResponse {
        // id에 해당하는 게시글의 댓글을 불러와서 request 로 업데이트 후 DB에 저장, replyresponse로 변환 후 반환
        // id에 해당하는 게시글 또는 댓글이 없다면 throw ModelNotFoundException
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId)


        reply.content = request.content

        return ReplyResponse.toReplyResponse(reply)
    }
}