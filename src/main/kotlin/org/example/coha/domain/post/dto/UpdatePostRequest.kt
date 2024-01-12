package org.example.coha.domain.post.dto


data class UpdatePostRequest(
    val content: String?,
    val title: String?
) {

//    fun toPost(userEmail: String): Post { //request받아온 걸 post로 변환
//        return Post(
//            title = title,
//            name = name,
//            content = content,
//            authorEmail = userEmail
//
//        )
    }
