package com.scott.blog.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

// Entities are POJOs representing data that can be persisted to the database.
// An Entity represents a table stored in a database.
@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue val id: Long = -1,
    val name: String = "",
    val email: String = ""
)