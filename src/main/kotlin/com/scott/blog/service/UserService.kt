package com.scott.blog.service

import com.scott.blog.PublicationTypeCreds
import com.scott.blog.model.Credentials
import com.scott.blog.repository.CredentialRepository
import com.scott.blog.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserService (private val userRepository: UserRepository, private val credentialRepository: CredentialRepository) {
    fun addCredentialInfo(username: String,
                          credUsername: String,
                          credPassword: String,
                          publicationTypeCreds: PublicationTypeCreds): Credentials {
        val user = userRepository.findByName(username)
        val creds = Credentials(user.get(),publicationTypeCreds, credUsername, credPassword)
        val credSaved = credentialRepository.save(creds)
        println("$publicationTypeCreds credentials for $username saved.")
        return credSaved
    }
}