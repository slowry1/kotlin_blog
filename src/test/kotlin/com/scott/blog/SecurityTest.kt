package com.scott.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.scott.blog.model.*
import com.scott.blog.repository.*
import com.scott.blog.service.UserService
import org.json.JSONObject
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import java.net.URI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityTest {

    @LocalServerPort
    var randomServerPort = 0

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var credentialRepository: CredentialRepository

    @Autowired
    lateinit var publicationRepository: PublicationRepository

    val endpoints = listOf<String>("/user", "/credential", "/post", "/publication")
    val testUserDefaultPw : String = "pw"
    val testRestTemplate = TestRestTemplate()
    var jackUserEditor : User = User()  // Editor
    var jillUserAuthor : User = User()  // Author
    var hulkUserNone : User = User()
    lateinit var jackBlogPost : Blog
    lateinit var jillBlogPost : Blog
    lateinit var facebookUserJack : Credentials
    lateinit var publicationBlogFB : Publication


    fun userService() : UserService {
        return UserService(userRepository, credentialRepository)
    }

    @BeforeAll
    internal fun setUp() {
        // Adding Roles
        val editorRole = Role(RoleType.Editor)
        val authorRole = Role(RoleType.Author)
        val noneRole = Role(RoleType.None)
        roleRepository.saveAll(mutableListOf(editorRole, authorRole, noneRole))
        println(">> 3 Roles Saved")

        // Adding Users
        val jackEntityEditor = User(TEST_DEFAULT_USERNAME_JACK, TEST_DEFAULT_USERNAME_JACK.plus("@gmail.com"), testUserDefaultPw, listOf(editorRole))
        jackUserEditor = userRepository.save(jackEntityEditor)
        println(">> $TEST_DEFAULT_USERNAME_JACK Saved")
        val jillEntityAuthor = User(TEST_DEFAULT_USERNAME_JILL, TEST_DEFAULT_USERNAME_JILL.plus("@gmail.com"), testUserDefaultPw, listOf(authorRole))
        jillUserAuthor = userRepository.save(jillEntityAuthor)
        println(">> $TEST_DEFAULT_USERNAME_JILL Saved")
        val hulkEntityNone = User(TEST_DEFAULT_USERNAME_HULK, TEST_DEFAULT_USERNAME_HULK.plus("@gmail.com"), testUserDefaultPw, listOf(noneRole))
        hulkUserNone = userRepository.save(hulkEntityNone)
        println(">> $TEST_DEFAULT_USERNAME_HULK Saved")

        // Adding Posts
        jackBlogPost = Blog("I brushed my teeth today!", jackUserEditor)
        postRepository.save(jackBlogPost)
        println(">> $TEST_DEFAULT_USERNAME_JACK Blog Post Saved")
        jillBlogPost = Blog("I put on deodorant!", jillUserAuthor)
        postRepository.save(jillBlogPost)
        println(">> $TEST_DEFAULT_USERNAME_JILL Blog Post Saved")


        // Adding Credentials
        facebookUserJack = userService().addCredentialInfo(
            TEST_DEFAULT_USERNAME_JACK, "jackUserEditornameFB",
            "jackPasswordFB", PublicationTypeCreds.FACEBOOK)


        credentialRepository.save(facebookUserJack)
        println(">> $TEST_DEFAULT_USERNAME_JACK Facebook Credential Saved")

        // Post Publications
        publicationBlogFB = Publication(jackBlogPost, PublicationType.FACEBOOK)
        publicationRepository.save(publicationBlogFB)
        println(">> $TEST_DEFAULT_USERNAME_JACK Blog Facebook Publication Posted")
    }

    @AfterAll
    internal fun tearDown(){
        publicationRepository.deleteAll()
        credentialRepository.deleteAll()
        postRepository.deleteAll()
        userRepository.deleteAll()
        roleRepository.deleteAll()
    }

    @Test
    fun testUnauthorizedEndpoints() {
        val baseUrl = "http://localhost:$randomServerPort"

        var uri: URI
        var response: ResponseEntity<String>

        for (item in endpoints) {
            uri = URI(baseUrl + item)
            response = testRestTemplate.withBasicAuth("invalid_user", "invalid_pw")
                .getForEntity<String>(uri, String::class.java)
            Assertions.assertEquals(401, response.statusCodeValue)
        }
    }

    @Test
    fun testAuthorizedEndpoints() {
        val baseUrl = "http://localhost:$randomServerPort"

        var uri: URI
        var response: ResponseEntity<String>

        for (item in endpoints) {
            uri = URI(baseUrl + item)
            response = testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_JACK, testUserDefaultPw)
                .getForEntity<String>(uri, String::class.java)
            Assertions.assertEquals(200, response.statusCodeValue)
            val responseBodyWithoutWhiteSpace = response.body?.replace("\\s".toRegex(), "")
            Assertions.assertNotNull(responseBodyWithoutWhiteSpace)
            if (responseBodyWithoutWhiteSpace != null) {
                Assertions.assertTrue(responseBodyWithoutWhiteSpace.contains("\"_links\":{\"self\":{\"href\":\"$uri\""))
            }
        }
    }

    @Test
    fun `test post endpoints - POST unauthenticated`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")

        val jsonObject = JSONObject()
        jsonObject.put("author", "$baseUrl/user/${jackUserEditor.id}")
        jsonObject.put("type", "blank")
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity(jsonObject.toString(), headers)

        // Post Post Test
        val response : ResponseEntity<String> = testRestTemplate
            .withBasicAuth("invalid_user", "invalid_pw")
            .postForEntity<String>(basePostUri, postRequest, String::class.java)// .getForEntity<String>(basePostUri, String::class.java)

        Assertions.assertEquals(401, response.statusCodeValue)
    }

    @Test
    fun `test post endpoints - GET unauthenticated`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")
        val response : ResponseEntity<String> = testRestTemplate
            .withBasicAuth("invalid_user", "invalid_pw")
            .getForEntity<String>(basePostUri, String::class.java)
        Assertions.assertEquals(401, response.statusCodeValue)
    }

    @Test
    fun `test post endpoints - POST Publish unauthenticated`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePublicationUri = URI("$baseUrl/publication")

        val pubJsonObject = JSONObject()
        pubJsonObject.put("publication", "$baseUrl/post/${jackBlogPost.id}")
        pubJsonObject.put("publicationType", "LOCAL")

        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity(pubJsonObject.toString(), headers)

        // Publish Post Test
        val testUserRoleEditorPostResponse =
            testRestTemplate.withBasicAuth("invalid_user", "invalid_pw")
                .postForEntity(basePublicationUri, postRequest, String::class.java)
        Assertions.assertEquals(401, testUserRoleEditorPostResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - DELETE unauthenticated`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")
        var deletePostUri = URI("$basePostUri/${jillBlogPost.id}")
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity<Post>(headers)

        // Delete any associated publications
        var publicationObjects = publicationRepository.findByPublication(jillBlogPost)
        publicationObjects.forEach {
            publicationRepository.delete(it)
        }
        // Delete Post Test
        val response = testRestTemplate.withBasicAuth("invalid_user", "invalid_pw")
            .exchange(deletePostUri, HttpMethod.DELETE, postRequest, String::class.java)
        Assertions.assertEquals(401, response.statusCodeValue)
    }

    @Test
    fun `test post endpoints - POST authenticated access granted`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")
        val jsonObject = JSONObject()
        jsonObject.put("author", "$baseUrl/user/${jackUserEditor.id}")
        jsonObject.put("type", "blank")
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity(jsonObject.toString(), headers)

        // Post Post Test
        val testUserRoleEditorPostResponse =
            testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_JACK, testUserDefaultPw)
                .postForEntity(basePostUri, postRequest, String::class.java)
        Assertions.assertEquals(201, testUserRoleEditorPostResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - POST authenticated access denied`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")

        val jsonObject = JSONObject()
        jsonObject.put("author", "$baseUrl/user/${jackUserEditor.id}")
        jsonObject.put("type", "blank")
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity(jsonObject.toString(), headers)

        // Post Post Test
        val testUserRoleEditorPostResponse =
            testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_HULK, testUserDefaultPw)
                .postForEntity(basePostUri, postRequest, String::class.java)
        Assertions.assertEquals(403, testUserRoleEditorPostResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - GET authenticated access granted`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")

        // Get Post Test
        val getResponse = testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_JACK, testUserDefaultPw)
            .getForEntity(basePostUri, String::class.java)
        Assertions.assertEquals(200, getResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - GET authenticated access denied`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")

        // Get Post Test
        val getResponse = testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_HULK, testUserDefaultPw)
            .getForEntity(basePostUri, String::class.java)
        Assertions.assertEquals(403, getResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - POST Publish authenticated access granted`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePublicationUri = URI("$baseUrl/publication")

        val pubJsonObject = JSONObject()
        pubJsonObject.put("publication", "$baseUrl/post/${jackBlogPost.id}")
        pubJsonObject.put("publicationType", "LOCAL")

        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity(pubJsonObject.toString(), headers)

        // Publish Post Test
        val testUserRoleEditorPostResponse =
            testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_JACK, testUserDefaultPw)
                .postForEntity(basePublicationUri, postRequest, String::class.java)
        Assertions.assertEquals(201, testUserRoleEditorPostResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - POST Publish authenticated access denied`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePublicationUri = URI("$baseUrl/publication")

        val pubJsonObject = JSONObject()
        pubJsonObject.put("publication", "$baseUrl/post/${jackBlogPost.id}")
        pubJsonObject.put("publicationType", "LOCAL")

        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity(pubJsonObject.toString(), headers)

        // Publish Post Test
        val testUserRoleEditorPostResponse =
            testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_HULK, testUserDefaultPw)
                .postForEntity(basePublicationUri, postRequest, String::class.java)
        Assertions.assertEquals(403, testUserRoleEditorPostResponse.statusCodeValue)
    }

    @Test
    fun `test post endpoints - DELETE authenticated access granted`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")
        var deletePostUri = URI("$basePostUri/${jillBlogPost.id}")
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity<Post>(headers)

        // Delete any associated publications
        var publicationObjects = publicationRepository.findByPublication(jillBlogPost)
        publicationObjects.forEach {
            publicationRepository.delete(it)
        }
        // Delete Post Test
        testRestTemplate.withBasicAuth(TEST_DEFAULT_USERNAME_JACK, testUserDefaultPw)
            .exchange(deletePostUri, HttpMethod.DELETE, postRequest, String::class.java)
        val tryToGetDeletedPost = postRepository.findAllByAuthor(jillUserAuthor)
        Assertions.assertEquals(0, tryToGetDeletedPost.size)
    }

    @Test
    fun `test post endpoints - DELETE authenticated access denied`() {
        val baseUrl = "http://localhost:$randomServerPort"
        var basePostUri = URI("$baseUrl/post")
        var deletePostUri = URI("$basePostUri/${jillBlogPost.id}")
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val postRequest = HttpEntity<Post>(headers)

        // Delete Post Test
        val response = testRestTemplate
            .withBasicAuth(TEST_DEFAULT_USERNAME_HULK, testUserDefaultPw)
            .exchange(deletePostUri, HttpMethod.DELETE, postRequest, String::class.java)
        Assertions.assertEquals(403, response.statusCodeValue)
    }
}