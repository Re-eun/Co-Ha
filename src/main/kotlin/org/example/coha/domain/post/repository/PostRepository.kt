package org.example.coha.domain.post.repository

import org.example.coha.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostRepository: JpaRepository<Post, Long> {
    @Modifying
    @Query(value = "update post set view = view + 1 where id=?", nativeQuery = true)
    fun updateViews(@Param("")id: Long)
    }


