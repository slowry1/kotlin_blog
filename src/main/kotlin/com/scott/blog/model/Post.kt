package com.scott.blog.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DiscriminatorOptions
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.rest.core.annotation.RestResource
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
// This was added to work with @RepositoryRestResource on the Post repo.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Blog::class, name = "blog"),
    JsonSubTypes.Type(value = HowTo::class, name = "how_to"),
    JsonSubTypes.Type(value = Blank::class, name = "blank")
)
open class Post(
    @ManyToOne
    open val author: User,

    open var title: String = ""
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0

    @CreationTimestamp
    open val date: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    open val updateDate: LocalDateTime = LocalDateTime.now()

}