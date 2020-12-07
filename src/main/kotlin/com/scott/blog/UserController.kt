package com.scott.blog

import org.springframework.web.bind.annotation.*

// This handles all the web requests
@RestController
class UserController (val repo : UserRepository) {

    @PostMapping("/user")
    fun addUser(@RequestBody user: User) {
        println("Adding User: $user")
        repo.save(user)
    }

    @GetMapping("/user")
    fun getAllUsers() = repo.findAll().toList()

    @GetMapping("/user/name/{name}")
    fun getUserByName(@PathVariable("name") name : String) = repo.findByName(name)

    @GetMapping("/user/email/{email}")
    fun getUserByEmail(@PathVariable("email") email : String) = repo.findByEmail(email)

}
