package com.scott.blog

import com.scott.blog.model.*
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PostService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var postRepository: PostRepository


    @BeforeAll
    internal fun setUp() {
        val jackEntity = User(-1,"jack","jack@gmail.com")
        val jackUser = userRepository.save(jackEntity)
        println(">> Jack Saved")
        val jillEntity = User(-1,"jill","jill@gmail.com")
        val jillUser = userRepository.save(jillEntity)
        println(">> Jill Saved")

        var jackBlogPost = Blog("I brushed my teeth today!")
        jackBlogPost.author = jackUser
        postRepository.save(jackBlogPost)
        println(">> Jack Blog Post Saved")

        var jackHowToPost = HowTo("First Step is to purchase the product so you dont need to build it.")
        jackHowToPost.author = jackUser
        postRepository.save(jackHowToPost)
        println(">> Jack How-To Post Saved")

        var jillBlankPost = Blank()
        jillBlankPost.author = jillUser
        postRepository.save(jillBlankPost)
        println(">> Jill Blank Post Saved")

    }

    @Test
    fun `test PostRepository findAllByAuthor` () {
        assertEquals(2, postRepository.findAllByAuthor(userRepository.findByName("jack").get()).size)
    }

    @Test
    fun `test PostRepository findAllByAuthorWithinXHours` () {
        val newSearchByTime = PostService().rewindTimeByHours(3)

        assertEquals(1,
            postRepository.findAllByAuthorWithinXHours(
                userRepository.findByName("jill").get(), newSearchByTime).size)
    }
}