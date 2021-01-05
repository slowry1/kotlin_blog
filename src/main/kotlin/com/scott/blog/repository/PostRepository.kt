package com.scott.blog.repository

import com.scott.blog.model.Post
import com.scott.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.time.LocalDateTime
import java.util.*

@RepositoryRestResource(path = "post")
interface PostRepository : JpaRepository <Post, Long> {
    fun findAllByAuthor(author: User) : List<Post>

    @Query("select p from Post p where p.date >= :newTimestamp and p.author = :author")
    fun findAllByAuthorWithinXHours(@Param("author") author: User, @Param("newTimestamp") newTimestamp: LocalDateTime ) : List<Post>
}
//@RepositoryRestResource(path = "post")
////interface PostRepository<T: Post> : JpaRepository <T, Long> {
//interface PostRepository<T: Post> : JpaRepository <T, Long> {
//    fun findAllByAuthor(author: User) : List<T>
//
//    @Query("select p from Post p where p.date >= :newTimestamp and p.author = :author")
//    fun findAllByAuthorWithinXHours(@Param("author") author: User, @Param("newTimestamp") newTimestamp: LocalDateTime ) : List<T>
//}