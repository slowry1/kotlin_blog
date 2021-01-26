package com.scott.blog.model

import javax.persistence.*

// TODO This is not used. this is when i was going down the rabbit hole and i went a simpler route instead.
//@Entity TODO took this away to try and do it with just role and user many to many
//class Privilege {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private val id: Long? = null
//
//    private val name: String? = null
//
//    @ManyToMany(mappedBy = "privileges")
//    private val roles: Collection<Role>? = null
//}