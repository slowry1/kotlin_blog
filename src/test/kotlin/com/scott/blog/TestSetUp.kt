package com.scott.blog

import com.scott.blog.model.*
import com.scott.blog.repository.CredentialRepository
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.PublicationRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PublicationService
import com.scott.blog.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

const val TEST_DEFAULT_USERNAME_JACK : String = "jack"
const val TEST_DEFAULT_USERNAME_JILL : String = "jill"

@TestConfiguration
//@Component
class TestSetUp {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
//    lateinit var postRepository: PostRepository<Post>
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var credentialRepository: CredentialRepository

    @Autowired
    lateinit var publicationRepository: PublicationRepository

    fun publicationService() : PublicationService {
        return PublicationService(publicationRepository, credentialRepository)
    }

    fun userService() : UserService{
        return UserService(userRepository, credentialRepository)
    }

    fun setUpUserPostPubCred() {
        val userList = setUpJackAndJillUsers()

        // Adding Posts
        val jackBlogPost = addBlogPost("I brushed my teeth today!", userList[0])
        println(">> Jack Blog Post Saved")
        val jackHowToPost = addHowToPost("First Step is to purchase the product so " +
                "you dont need to build it.", userList[0])
        println(">> Jack How-To Post Saved")
        val jillBlankPost = addBlankPost(userList[1])
        println(">> Jill Blank Post Saved")

        // Adding Credentials
        val facebookUser = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JACK, "jackUsernameFB",
            "jackPasswordFB", PublicationTypeCreds.FACEBOOK)
        val twitterUser = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JILL, "jillUsernameTwitter",
            "jillPasswordTwitter", PublicationTypeCreds.TWITTER)
        credentialRepository.saveAll(mutableListOf(facebookUser, twitterUser))
        println(">> Jack Facebook Credential Saved")
        println(">> Jill Twitter Credential Saved")

        // Post Publications
        val publicationBlogLocal = Publication(jackBlogPost, PublicationType.LOCAL)
        val publicationHowToTwitter = Publication(jackHowToPost, PublicationType.TWITTER)
        val publicationBlankFacebook = Publication(jillBlankPost, PublicationType.FACEBOOK)
        publicationRepository.saveAll(
            mutableListOf(publicationBlogLocal, publicationBlankFacebook, publicationHowToTwitter))
        println(">> Jack Blog Local Publication Posted")
        println(">> Jack How-To Twitter Publication Posted")
        println(">> Jill Blank Facebook Publication Posted")

    }

    fun setUpJackAndJillUsers() : List<User>{
        // Adding Users
        val jackUser = addUser(TEST_DEFAULT_USERNAME_JACK)
        println(">> Jack Saved")
        val jillUser = addUser(TEST_DEFAULT_USERNAME_JILL)
        println(">> Jill Saved")
        return listOf(jackUser, jillUser)
    }

    fun addUser(name: String) : User{
        val userEntity = User(name, name.plus("@gmail.com"))
        return userRepository.save(userEntity)
    }

    fun addBlogPost(dailyActivity: String, user: User) : Blog{
        val jackBlogEntity = Blog(dailyActivity, user)
        return postRepository.save(jackBlogEntity)
    }

    fun addBlankPost(user: User) : Blank{
        val jackBlankEntity = Blank(user)
        return postRepository.save(jackBlankEntity)
    }

    fun addHowToPost(tutorial: String, user: User) : HowTo{
        val jackHowToEntity = HowTo(tutorial, user)
        return postRepository.save(jackHowToEntity)
    }


}