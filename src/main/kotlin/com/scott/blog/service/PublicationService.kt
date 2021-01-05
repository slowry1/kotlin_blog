package com.scott.blog.service

import com.scott.blog.*
import com.scott.blog.model.*
import com.scott.blog.repository.CredentialRepository
import com.scott.blog.repository.PublicationRepository
import org.springframework.stereotype.Component

@Component
class PublicationService(val publicationRepository: PublicationRepository, val credentialRepository: CredentialRepository) {

    fun publishPosts(post: Post): List<Publication> {
        val publications = publicationRepository.findByPublication(post)
        // TODO("This is a placeholder if i have time to look into the strategy stuff so I dont have to do switch statement")
        for (publication in publications) {
            when (publication.publicationType) {
                PublicationType.LOCAL -> Local().printTitleToConsole(post)
                PublicationType.TWITTER -> {
                    // TODO breaks if this returns null
                    val creds = credentialRepository.findByUserAndPublicationType(
                        publication.publication.author, PublicationTypeCreds.TWITTER)
                    val username = creds.username
                    val password = creds.password
                    Twitter().printTitleToConsole(post)
                }
                PublicationType.FACEBOOK -> {
                    // TODO breaks if this returns null
                    val creds = credentialRepository.findByUserAndPublicationType(
                        publication.publication.author, PublicationTypeCreds.FACEBOOK)
                    val username = creds.username
                    val password = creds.password
                    Facebook().printTitleToConsole(post)
                }
            }
        }
        return publications
    }

    fun publicationTypeObjectFromPublicationTypeEnum(publicationType: PublicationType): Any {
        return when (publicationType) {
            PublicationType.LOCAL -> Local()
            PublicationType.TWITTER -> Twitter()
            PublicationType.FACEBOOK -> Facebook()
        }
    }

//    fun postTypeFromPublication(publication: Publication, user: User): Post {
//        return when (publication.publication::class) {
//            Blank::class -> Blank(user)
//            Blog::class -> Blog(user)
//            HowTo::class -> HowTo(user)
//        }
//    }
}