package com.scott.blog.controller

import com.scott.blog.model.Blog
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

//@RestController
//class BlogController (val repo : PostRepository, val userRepo : UserRepository) {
//
//    @Autowired
//    lateinit var postService : PostService
//
//    @PostMapping("/blog")
//    fun addBlog() {//@RequestBody blank: Blank) {
//        // TODO use data from RequestBody
//        println("Adding blog: 1")
//        val user = userRepo.findByName("jam")
//        println("Adding blog: ${user.get()}")
//        println("Adding blog: 2")
//        var blog = Blog("I brushed my teeth today!", user.get())
//        println("Adding blog: 3")
//        var returnedBlog = repo.save(blog)
//        println("Adding blog: 5 $returnedBlog")
//        println("Adding blog: 5 ${returnedBlog.dailyActivity}")
//
//    }
//
//}