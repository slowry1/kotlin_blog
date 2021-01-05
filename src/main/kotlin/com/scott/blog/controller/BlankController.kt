package com.scott.blog.controller

import com.scott.blog.model.Blank
import com.scott.blog.model.Post
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

// This handles all the web requests
//@RestController
//class BlankController (val repo : PostRepository, val userRepo : UserRepository) {
//
//    @Autowired
//    lateinit var postService : PostService
//
//    @PostMapping("/blank")
//    fun addBlank(){//@RequestBody blank: Blank) {
//        // TODO use data from RequestBody
//        println("Adding blank: 1")
//        val user = userRepo.findByName("jam")
//        println("Adding blank: ${user.get()}")
//        println("Adding blank: 2")
//        var blank = Blank(user.get())
//        println("Adding blank: 3")
//        repo.save(blank)
//
//
////        blank.author = user.get()
////        repo.save(blank)
//    }
//
//    @GetMapping("/blank")
//    fun getAllBlank(): MutableList<Post> {
//        return repo.findAll()
//    }
//
//    @GetMapping("/blank/authors")///{author}")
//    fun getBlankByAuthor(){//@PathVariable("author") author : String) {
//        val user = userRepo.findByName("jam")
//        val post = repo.findAllByAuthor(user.get())
//        println(post[0])
//        println(post[0].date)
//        println(post[0].date::class.simpleName)
//        println(post[0].date::class.qualifiedName)
//    }
//
//    @GetMapping("/blank/author/{author}/{hours}")
//    fun getBlankByAuthorAfterXHours(
//        @PathVariable("author") author : String,
//        @PathVariable("hours") hours : Long): List<Post> {
//
//        val user = userRepo.findByName(author)
//        val newTimeStamp = postService.rewindTimeByHours(hours)
//        println("Getting all posts by author $author between now and $newTimeStamp")
//        return repo.findAllByAuthorWithinXHours(user.get(), newTimeStamp)
//    }
//}
