package com.scott.blog.controller

import com.scott.blog.model.Blog
import com.scott.blog.model.HowTo
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

//@RestController
//class HowToController (val repo : PostRepository, val userRepo : UserRepository) {
//
//    @Autowired
//    lateinit var postService : PostService
//
//    @PostMapping("/how-to")
//    fun addHowTo() {//@RequestBody blank: Blank) {
//        // TODO use data from RequestBody
//        println("Adding howTo: 1")
//        val user = userRepo.findByName("jam")
//        println("Adding howTo: ${user.get()}")
//        println("Adding howTo: 2")
//        var howTo = HowTo("First Step is to purchase the product so you dont need to build it.", user.get())
//        println("Adding howTo: 3")
//        var returnedhowTo = repo.save(howTo)
//        println("Adding howTo: 5 $returnedhowTo")
//        println("Adding howTo: 5 ${returnedhowTo.tutorial}")
//    }
//}