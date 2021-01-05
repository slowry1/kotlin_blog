package com.scott.blog

import com.scott.blog.model.*
import com.scott.blog.repository.CredentialRepository
import com.scott.blog.repository.PostRepository
import com.scott.blog.repository.PublicationRepository
import com.scott.blog.repository.UserRepository
import com.scott.blog.service.PublicationService
import com.scott.blog.service.UserService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.reflect.typeOf

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PublicationTest {


    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
//    lateinit var postRepository: PostRepository<Post>
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var publicationRepository: PublicationRepository

    @Autowired
    lateinit var credentialRepository: CredentialRepository

    fun userService() : UserService{
        return UserService(userRepository, credentialRepository)
    }

    fun publicationService() : PublicationService{
        return PublicationService(publicationRepository, credentialRepository)
    }
//    @Autowired
//    lateinit var testSetUp: TestSetUp

//    @Autowired
//    lateinit var publicationService: PublicationService

//
//    @Autowired
//    lateinit var userService: UserService


    @BeforeAll
    internal fun setUp() {
//        This didnt work i kept getting errors so i am just doing it like this for every test setup
//        val testSetUp = TestSetUp()
//        testSetUp.setUpUserPostPubCred()

        // Adding Users
        val jackEntity = User(TEST_DEFAULT_USERNAME_JACK, TEST_DEFAULT_USERNAME_JACK.plus("@gmail.com"))
        val jackUser = userRepository.save(jackEntity)
        println(">> Jack Saved")
        val jillEntity = User(TEST_DEFAULT_USERNAME_JILL, TEST_DEFAULT_USERNAME_JILL.plus("@gmail.com"))
        val jillUser = userRepository.save(jillEntity)
        println(">> Jill Saved")


        // Adding Posts
        var jackBlogPost = Blog("I brushed my teeth today!", jackUser)
        postRepository.save(jackBlogPost)
        println(">> Jack Blog Post Saved")

        var jackHowToPost = HowTo("First Step is to purchase the product so you dont need to build it.", jackUser)
        postRepository.save(jackHowToPost)
        println(">> Jack How-To Post Saved")

        var jillBlankPost = Blank(jillUser)
        postRepository.save(jillBlankPost)
        println(">> Jill Blank Post Saved")


        // Adding Credentials
        val facebookUserJack = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JACK, "jackUsernameFB",
            "jackPasswordFB", PublicationTypeCreds.FACEBOOK)
        val facebookUserJill = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JILL, "jillUsernameFB",
            "jillPasswordFB", PublicationTypeCreds.FACEBOOK)
        val twitterUserJack = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JACK, "jackUsernameTwitter",
            "jackPasswordTwitter", PublicationTypeCreds.TWITTER)
        val twitterUserJill = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JILL, "jillUsernameTwitter",
            "jillPasswordTwitter", PublicationTypeCreds.TWITTER)

        credentialRepository.saveAll(
            mutableListOf(facebookUserJill, twitterUserJack, facebookUserJack, twitterUserJill))
        println(">> Jack Facebook & Twitter Credential Saved")
        println(">> Jill Facebook & Twitter Credential Saved")

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

    @AfterAll
    internal fun tearDown(){
        publicationRepository.deleteAll()
        credentialRepository.deleteAll()
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `test PublicationRepository findByPublication` () {
        val jackUserObject = userRepository.findByName(TEST_DEFAULT_USERNAME_JACK)
        val jackPostObject = postRepository.findAllByAuthor(jackUserObject.get())
        var jackBlogObject : Blog? = null
        run loop@{
            jackPostObject.forEach {
                if (it is Blog) {
                    jackBlogObject = it
                    return@loop
                }
            }
        }
        Assertions.assertEquals(Blog::class, jackBlogObject!!::class) //TOdo need to make sure this is the one that i want to get ie blog.
        val jackPublicationObject = publicationRepository.findByPublication(jackBlogObject!!)
        Assertions.assertEquals(1, jackPublicationObject.size)
        Assertions.assertEquals(jackBlogObject, jackPublicationObject[0].publication)
    }

    @Test
    fun `test PublicationService PublishPosts` () {
        val jillUserObject = userRepository.findByName(TEST_DEFAULT_USERNAME_JILL)
        val jillPostObject = postRepository.findAllByAuthor(jillUserObject.get())
        var jillBlankObject : Blank? = null
        run loop@{
            jillPostObject.forEach {
                if (it is Blank) {
                    jillBlankObject = it
                    return@loop
                }
            }
        }
        var jillPublicationObject = publicationRepository.findByPublication(jillBlankObject!!)
        Assertions.assertEquals(1, jillPublicationObject.size)
        publicationService().publishPosts(jillBlankObject!!)

    }
}