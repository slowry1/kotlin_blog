package com.scott.blog

import com.scott.blog.model.*
import com.scott.blog.repository.CredentialRepository
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.PublicationRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class CredentialTest {


    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
//    lateinit var postRepository: PostRepository<Post>
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var publicationRepository: PublicationRepository

    @Autowired
    lateinit var credentialRepository: CredentialRepository

    fun userService() : UserService {
        return UserService(userRepository, credentialRepository)
    }


    @BeforeAll
    internal fun setUp() {
        // Adding Users
        val jackEntity = User(TEST_DEFAULT_USERNAME_JACK, TEST_DEFAULT_USERNAME_JACK.plus("@gmail.com"))
        val jackUser = userRepository.save(jackEntity)
        println(">> Jack Saved")

        // Adding Posts
        var jackHowToPost = HowTo("I brushed my teeth today!", jackUser)
        postRepository.save(jackHowToPost)
        println(">> Jack Blog Post Saved")

        // Adding Credentials
        val twitterUserJack = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JACK, "jackUsernameTwitter",
            "jackPasswordTwitter", PublicationTypeCreds.TWITTER)

        credentialRepository.save(twitterUserJack)
        println(">> Jack Twitter Credential Saved")

        // Post Publications
        val publicationHowToTwitter = Publication(jackHowToPost, PublicationType.TWITTER)
        publicationRepository.save(publicationHowToTwitter)
        println(">> Jack How-To Twitter Publication Posted")
    }

    @AfterAll
    internal fun tearDown(){
        publicationRepository.deleteAll()
        credentialRepository.deleteAll()
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `test CredentialRepository findByUserAndPublicationType` () {
        val jackUserObject = userRepository.findByName(TEST_DEFAULT_USERNAME_JACK)
        val jackPostObject = postRepository.findAllByAuthor(jackUserObject.get())
        val jackPublications = publicationRepository.findByPublication(jackPostObject[0])
        jackPublications.forEach{
            val creds = credentialRepository.findByUserAndPublicationType(it.publication.author, PublicationTypeCreds.TWITTER)
            Assertions.assertEquals(Credentials::class, creds::class)
            Assertions.assertEquals("jackUsernameTwitter", creds.username)
            Assertions.assertEquals("jackPasswordTwitter", creds.password)
        }
    }

}