package com.scott.blog.model

import com.scott.blog.PublicationType
import com.scott.blog.PublicationTypeCreds
import javax.persistence.*

@Entity
class Credentials(
    @ManyToOne
    val user: User?,

    @Enumerated(EnumType.STRING)
    var publicationType: PublicationTypeCreds,

    var username: String,

    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}