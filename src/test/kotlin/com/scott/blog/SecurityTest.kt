package com.scott.blog

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.boot.web.server.LocalServerPort
import java.net.URI
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.web.client.TestRestTemplate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityTest {

    @LocalServerPort
    var randomServerPort = 0

    val testRestTemplate = TestRestTemplate()

    val endpoints = listOf<String>("/user", "/credential", "/post", "/publication")

// NOT NEEDED ANYMORE
//    lateinit var restTemplate: RestTemplate
//
//    @BeforeAll
//    internal fun setUp() {
//        restTemplate = RestTemplate(getClientHttpRequestFactory()!!)
//    }
//
//    private fun getClientHttpRequestFactory(username: String, pw: String): HttpComponentsClientHttpRequestFactory? {
//        val clientHttpRequestFactory = HttpComponentsClientHttpRequestFactory()
//        clientHttpRequestFactory.httpClient = httpClient(username, pw)
//        return clientHttpRequestFactory
//    }
//
//    private fun httpClient(username: String, pw: String): HttpClient {
//        val credentialsProvider: CredentialsProvider = BasicCredentialsProvider()
//        credentialsProvider.setCredentials(
//            AuthScope.ANY,
//            UsernamePasswordCredentials(username, pw)
//        )
//        return HttpClientBuilder
//            .create()
//            .setDefaultCredentialsProvider(credentialsProvider)
//            .build()
//    }

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
            response = testRestTemplate.withBasicAuth("scott", "pw")
                .getForEntity<String>(uri, String::class.java)
            Assertions.assertEquals(200, response.statusCodeValue)
            val responseBodyWithoutWhiteSpace = response.body?.replace("\\s".toRegex(), "")
            Assertions.assertNotNull(responseBodyWithoutWhiteSpace)
            if (responseBodyWithoutWhiteSpace != null) {
                Assertions.assertTrue(responseBodyWithoutWhiteSpace.contains("\"_links\":{\"self\":{\"href\":\"$uri\""))
            }

        }
    }

//    @Test
//    fun testUnauthorizedEnpoints() {
//        val restTemplate = RestTemplate()//getClientHttpRequestFactory("scott", "pw")!!)
//        val baseUrl = "http://localhost:" + randomServerPort.toString() + "/user"
//        val uri = URI(baseUrl)
////        val result: ResponseEntity<String> = restTemplate.getForEntity(uri, String::class.java)
//        restTemplate.errorHandler.handleError(uri, )
////        restTemplate.getForEntity(uri, String::class.java)
////        Assertions.assertEquals(401, restTemplate.getForEntity(uri, String::class.java).statusCodeValue)
//        //Verify request succeed
//       // Assertions.assertEquals(200, result.statusCodeValue)
////        Assertions.assertEquals(401, result.statusCodeValue)
////        Assertions.assertEquals(true, result.body!!.contains("employeeList"))
//    }
//
//    @Test
//    fun testAuthorizedEnpoints() {
//        val restTemplate = RestTemplate(getClientHttpRequestFactory("scott", "pw")!!)
//        val baseUrl = "http://localhost:" + randomServerPort.toString() + "/user"
//        val uri = URI(baseUrl)
//        val result: ResponseEntity<String> = restTemplate.getForEntity(uri, String::class.java)
//
//        //Verify request succeed
//        Assertions.assertEquals(200, result.statusCodeValue)
////        Assertions.assertEquals(401, result.statusCodeValue)
////        Assertions.assertEquals(true, result.body!!.contains("employeeList"))
//    }





}