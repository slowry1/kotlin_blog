package com.scott.blog

import com.scott.blog.model.User
import com.scott.blog.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository


    @BeforeAll
    internal fun setUp() {
        val jackEntity = User(-1,"jack","jack@gmail.com")
        userRepository.save(jackEntity)
        println(">> Jack Saved")
        val jillEntity = User(-1,"jill","jill@gmail.com")
        userRepository.save(jillEntity)
        println(">> Jill Saved")
        val bobEntity = User(-1, "bob","bob@gmail.com")
        userRepository.save(bobEntity)
        println(">> bob Saved")

    }

    @Test
    fun `test UserRepository findByName Optional` () {
        assertEquals("jack", userRepository.findByName("jack").get().name)
    }

    @Test
    fun `test UserRepository findByEmail List` () {
        assertEquals("jill@gmail.com", userRepository.findByEmail("jill@gmail.com")[0].email)
    }

    @Test
    fun `test add user`(){
        val bobEntity = User(-1, "bob","bob@gmail.com")
        assertEquals(User::class, userRepository.save(bobEntity)::class)
    }

//    @Test
//    // TODO This may be an issue because i am just initializing the user instead of having variable i can modify
//    fun `test update user`(){
//        val bob = userRepository.findByName("bob")
//
//    }

    @Test
    fun `test delete user`(){
        val bob = userRepository.findByName("bob").get()
        userRepository.delete(bob)
        val bobDeleted = userRepository.findByName("bob")
        assertTrue(bobDeleted.isEmpty)
    }

}