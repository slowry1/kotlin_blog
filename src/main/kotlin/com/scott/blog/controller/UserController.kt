package com.scott.blog.controller

import com.scott.blog.PublicationTypeCreds
import com.scott.blog.model.User
import com.scott.blog.repository.CredentialRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

// This handles all the web requests
//@RestController
//class UserController (val userRepository : UserRepository, val credentialRepository: CredentialRepository) {
//
//    @Autowired
//    lateinit var userService: UserService
//
//    @PostMapping("/user")
//    fun addUser(@RequestBody user: User) {
//        println("Adding User: $user")
//        userRepository.save(user)
//    }
//
//    @PostMapping("/user/creds")
//    fun addUserCreds() {
//        var facebookUser = userService.addCredentialInfo("jam", "jamUsernameFB", "jamPasswordFB",
//            PublicationTypeCreds.FACEBOOK
//        )
//        var twitterUser = userService.addCredentialInfo("jam", "jamUsernameTwitter", "jamPasswordTwitter",
//            PublicationTypeCreds.TWITTER
//        )
//
//        credentialRepository.saveAll(mutableListOf(facebookUser, twitterUser))
//    }
//
//    @GetMapping("/user")
//    fun getAllUsers() = userRepository.findAll().toList()
//
////    @GetMapping("/user/name/{name}", produces = arrayOf(MediaTypes.HAL_JSON_VALUE))
////    fun getUserByName(@PathVariable("name") name : String): ResponseEntity<User> {
////        var user = userRepository.findByName(name)
////        val link = linkTo<UserController> {
////            methodOn(UserController::class.java).getUserByName(name)
////        }.withSelfRel()
////        user.get().add(link)
////
////        return ResponseEntity(user.get(), HttpStatus.OK)
////    }
//    @GetMapping("/user/name/{name}")
//    fun getUserByName(@PathVariable("name") name : String): Optional<User> {
//
//        return userRepository.findByName(name)
//    }
//
//    @GetMapping("/user/email/{email}")
//    fun getUserByEmail(@PathVariable("email") email : String) = userRepository.findByEmail(email)
//
//    @PutMapping("/user/{id}")
//    fun updateUser(@PathVariable("id") id: Long, @RequestBody user: User){
//        var userToUpdate = userRepository.findById(id)
//        //TODO COME BACK TO THIS LATER
//    }
//
//    @DeleteMapping("/user/{id}")
//    fun deleteUser(@PathVariable("id") id: Long){
//        userRepository.deleteById(id)
//    }
//}
