package com.scott.blog.model

import com.scott.blog.PublicationType
import javax.persistence.*

@Entity
class Publication(
    @ManyToOne
    open var publication: Post,

    @Enumerated(EnumType.STRING)
    var publicationType: PublicationType
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}