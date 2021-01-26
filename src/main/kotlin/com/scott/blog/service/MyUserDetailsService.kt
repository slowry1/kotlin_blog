package com.scott.blog.service

//import com.scott.blog.MyUserPrincipal
import com.scott.blog.model.User
import com.scott.blog.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

// TODO This is not used. this is when i was going down the rabbit hole and i went a simpler route instead.
@Service
class MyUserDetailsService : UserDetailsService { // demo implemented AuthenticationProvider

    @Autowired
    lateinit var userRepository : UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username : String): UserDetails {
//        val user : Optional<User> = userRepository.findByName(username)
//        user ?: throw UsernameNotFoundException(username)
        return userRepository.findByName(username).get() ?: throw UsernameNotFoundException(username) //MyUserPrincipal(user.get())
    }
}