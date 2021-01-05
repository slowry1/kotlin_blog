package com.scott.blog.repository

import com.scott.blog.PublicationTypeCreds
import com.scott.blog.model.Credentials
import com.scott.blog.model.Post
import com.scott.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "credential")
interface CredentialRepository : JpaRepository<Credentials, Long> {
    fun findByUserAndPublicationType(user: User?, publicationTypeCreds: PublicationTypeCreds) : Credentials
}