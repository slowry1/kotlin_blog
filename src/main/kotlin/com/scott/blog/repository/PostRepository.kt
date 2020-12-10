package com.scott.blog.repository

import com.scott.blog.model.Post
import com.scott.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Timestamp
import java.util.*

interface PostRepository : JpaRepository <Post, Long> {
    fun findAllByAuthor(author: User) : List<Post>

    @Query("select p from Post p where p.date >= :newDateTime and p.author = :author")
    fun findAllByAuthorWithinXHours(@Param("author") author: User, @Param("newDateTime") newDateTime: Timestamp ) : List<Post>
}