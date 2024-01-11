package org.example.coha.domain.post.dto

import org.example.coha.domain.post.model.Post

data class UpdatePostRequest(

    val id: Long?,
    val content: String,



) {

    fun post(): Updatepost{
        return  Updatepost(
            content = content,

                    )
    }

    class Updatepost(content: String, ) {

    }
}
