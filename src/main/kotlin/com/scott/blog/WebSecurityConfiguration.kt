package com.scott.blog

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var customAuthenticationEntryPoint : CustomAuthenticationEntryPoint

    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic { authenticationEntryPoint = customAuthenticationEntryPoint }
//            formLogin {  }
            authorizeRequests {
                authorize("/user/**", hasAuthority("ROLE_USER") )
                authorize("/post/**", hasAuthority("ROLE_USER") )
                authorize("/credential/**", hasAuthority("ROLE_USER") )
                authorize("/profile/**", hasAuthority("ROLE_USER") )
                authorize("/publication/**", hasAuthority("ROLE_USER") )
                authorize("/**")

            }

        }
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        auth?.inMemoryAuthentication()
            ?.withUser("scott")
            ?.password(encoder.encode("pw"))
            ?.roles("USER")
            ?.and()
            ?.withUser("MrRobot")
            ?.password(encoder.encode("pw1"))
            ?.roles("USER", "ADMIN")
    }
}