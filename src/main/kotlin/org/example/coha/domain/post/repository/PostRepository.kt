package org.example.coha.domain.post.repository

import org.example.coha.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {


}