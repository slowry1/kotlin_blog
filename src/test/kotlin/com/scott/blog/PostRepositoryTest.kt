package com.scott.blog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.scott.blog.model.*
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PostService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
//    lateinit var postRepository: PostRepository<Post>
    lateinit var postRepository: PostRepository
    val mapper = jacksonObjectMapper()


    @BeforeAll
    internal fun setUp() {
        val jackEntity = User("jack","jack@gmail.com")
        val jackUser = userRepository.save(jackEntity)
        println(">> Jack Saved")
        val jillEntity = User("jill","jill@gmail.com")
        val jillUser = userRepository.save(jillEntity)
        println(">> Jill Saved")

        var jackBlogPost = Blog("I brushed my teeth today!", jackUser)
        var postResponse = postRepository.save(jackBlogPost)
        println(">> Jack Blog Post Saved")
        println(">> Jack Blog Post Saved ${mapper.writeValueAsString(jackBlogPost)}")
        println(">> Jack Blog Post Saved ${mapper.writeValueAsString(postResponse)}")

        var jackHowToPost = HowTo("First Step is to purchase the product so you dont need to build it.", jackUser)
        postRepository.save(jackHowToPost)
        println(">> Jack How-To Post Saved")

        var jillBlankPost = Blank(jillUser)
        postRepository.save(jillBlankPost)
        println(">> Jill Blank Post Saved")
    }
    @AfterAll
    internal fun tearDown(){
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `test PostRepository findAllByAuthor` () {
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>22222  >>>     ${userRepository.findByName("jack")}")
        println("******************     ${postRepository.findAllByAuthor(userRepository.findByName("jack").get())}")
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&     ${postRepository.findAllByAuthor(userRepository.findByName("jack").get()).size}")
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println(postRepository.findAllByAuthor(userRepository.findByName("jack").get()).size)
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println(postRepository.findAllByAuthor(userRepository.findByName("jack").get()).size)
        var one = postRepository.findAll()
        var two = postRepository.findAll()[0]
        println("1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("WOW1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       ${postRepository.findAll().size}")
        println("WOW2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       ${postRepository.findAll()[0]}")
        println("WOW3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       ${postRepository.findAll()[1]}")
        println("5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        val serialized = mapper.writeValueAsString(postRepository.findAll()[0])

        println("WOW2 serialized >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>       $serialized")

        println("6>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        println("7>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
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