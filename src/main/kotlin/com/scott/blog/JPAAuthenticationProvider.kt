package com.scott.blog

import com.scott.blog.model.User
import com.scott.blog.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class JPAAuthenticationProvider : AuthenticationProvider {
    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun authenticate(authentication: Authentication?): Authentication? {
        var authenticationToken: UsernamePasswordAuthenticationToken? = null
        var name: String? = authentication?.name
        var password: String? = authentication?.credentials.toString()
        var user: User? = name?.let { userRepository.findByName(it).get() }

        if (user != null) {
            if (name.equals(user.name) and password.equals(user.password)) {//Dont need this because pw is not encrypted   BCrypt.checkpw(password, user.password)) {
                val grantedAuthority = user.authorities//Changed this to use the one in user class getGrantedAuthorities(user)
                authenticationToken = UsernamePasswordAuthenticationToken(name, password, grantedAuthority)
            }
        } else
            throw UsernameNotFoundException("User $name not found")

        return authenticationToken
    }

    override fun supports(authentication: Class<*>?): Boolean {
            return authentication!!.equals(UsernamePasswordAuthenticationToken::class.java)
    }

//    fun getGrantedAuthorities(user: User): MutableCollection<out GrantedAuthority> {
//        var grantedAuthorities : MutableCollection<GrantedAuthority> = ArrayList()
//        for (role in user.roles) {
//            when (role.authority) {
//                RoleType.Editor.toString() -> grantedAuthorities.add((SimpleGrantedAuthority("ROLE_EDITOR")))
//                RoleType.Author.toString() -> grantedAuthorities.add((SimpleGrantedAuthority("ROLE_AUTHOR")))
//            }
//        }
//        return grantedAuthorities
//    }
}