package com.scott.blog.model

import javax.persistence.*

// Entities are POJOs representing data that can be persisted to the database.
// An Entity represents a table stored in a database.
@Entity
@Table(name = "users")
class User (
    val name: String = "",
    val email: String = ""
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0;

}