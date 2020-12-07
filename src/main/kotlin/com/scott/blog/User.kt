package com.scott.blog

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

// Entities are POJOs representing data that can be persisted to the database.
// An Entity represents a table stored in a database.
@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue val id: Long,
    val name: String,
    val email: String
)