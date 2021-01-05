package com.scott.blog.repository

import com.scott.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(path = "user")
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name : String) : Optional<User>

    fun findByEmail(email : String) : List<User>
}