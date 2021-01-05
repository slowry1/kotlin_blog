package com.scott.blog.repository

import com.scott.blog.model.Post
import com.scott.blog.model.Publication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "publication")
interface PublicationRepository : JpaRepository<Publication, Long> {
    fun findByPublication(publication: Post) : List<Publication>
}