package com.scott.blog

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class BlogRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

//    @Autowired
//    lateinit var entityManager: TestEntityManager // You can use this with persist and flush if you want to work do multiple things in the transaction or we can do what I did an just use userRepository.save. This will finish the transaction immediately.


    @BeforeAll
    internal fun setUp() {
        val jackEntity = User(-1,"jack","jack@gmail.com")
        userRepository.save(jackEntity)
        println(">> Jack Saved")
        val jillEntity = User(-1,"jill","jill@gmail.com")
        userRepository.save(jillEntity)
        println(">> Jill Saved")
    }

    @Test
    fun `test UserRepository findByName` () {
        assertEquals("jack", userRepository.findByName("jack").name)
    }

    @Test
    fun `test UserRepository findByEmail` () {
        assertEquals("jill@gmail.com", userRepository.findByEmail("jill@gmail.com").email)
    }

}