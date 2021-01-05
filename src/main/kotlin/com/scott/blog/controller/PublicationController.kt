package com.scott.blog.controller

import com.scott.blog.model.Publication
import com.scott.blog.repository.PublicationRepository
import com.scott.blog.service.PublicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
//import com.scott.blog.service.PublicationService

//@RestController
//class PublicationController (val pubRepo: PublicationRepository) {
//
//    @Autowired
//    lateinit var publicationService: PublicationService
//
//    @PostMapping("/publication")
//    fun addPublication(@RequestBody publication: Publication) {
//        pubRepo.save(publication)
//        publicationService.publishPosts(publication.publication)
//    }
//
//    @PutMapping("/publication/{id}")
//    fun updatePublication(@PathVariable("id") id: Long, @RequestBody publication: Publication) {
//        pubRepo.save(publication)
//        publicationService.publishPosts(publication.publication)
//    }
//
////    @GetMapping("/publication/{id}")
////    fun getPublicationById(@PathVariable("id") id: Long): Publication {
////        return pubRepo.findById(id).get()
////    }
//    @GetMapping("/publication/{id}", produces = arrayOf(MediaTypes.HAL_JSON_VALUE))
//    fun getPublicationById(@PathVariable("id") id: Long): ResponseEntity<Publication> {
//        var publication = pubRepo.findById(id).get()
//        val link = linkTo<PublicationController> {
//            methodOn(PublicationController::class.java).getPublicationById(id)
//        }.withSelfRel()
//        publication.add(link)
//    return ResponseEntity(publication, HttpStatus.OK)
//    }
//
//    @GetMapping("/publication")
//    fun getAllPublications(): MutableList<Publication> {
//        return pubRepo.findAll()
//    }
////    @GetMapping("/publication", produces = [MediaTypes.HAL_JSON_VALUE])
////    fun getAllPublications(): ResponseEntity<Publication> {
////        var publicaiton = pubRepo.findAll()
////        val link = linkTo<PublicationController> {
////            methodOn(PublicationController::class.java).getUserByName(name)
////        }.withSelfRel()
////        user.get().add(link)
////    }
//
////    @DeleteMapping("/publication/{id}")
////    fun deletePublication(@PathVariable("id") id: Long) {
////        return pubRepo.deleteById(id)
////    }
//    @DeleteMapping("/publication/{id}")
//    fun deletePublication(@PathVariable("id") id: Long) {
//        return pubRepo.deleteById(id)
//    }
//}