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
//@RestResource(path = "post")
//   TODO Look at doing this when i get back because this might be the only way to set the type of the post. Look at the blank class to see the @JsonTypeName that is used
//  TODO the json post will look something like this= {"author": {Fill with user stuff},"title": "whatever", "post": {"type": "blank" }}
//  TODO here is you tube i was looking at https://www.youtube.com/watch?v=IlLC3Yetil0
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "thing")
@JsonSubTypes(
    JsonSubTypes.Type(value = Blog::class, name = "blog"),
    JsonSubTypes.Type(value = HowTo::class, name = "how_to"),
    JsonSubTypes.Type(value = Blank::class, name = "blank")
)
//    @Type(value = Blog::class, name = "blog"),
//    @Type(value = HowTo::class, name = "how_to"),
//    @Type(value = Blank::class, name = "blank")})//@JsonSubTypes.Type(value = Blog::class)})//
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