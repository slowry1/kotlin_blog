package com.scott.blog.repository

import com.scott.blog.model.Role
import com.scott.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository
import java.util.*

@RepositoryRestResource(path = "role")
interface RoleRepository  : JpaRepository<Role, Long> {
    fun findByRolename(rolename : String) : Role

}