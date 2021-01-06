package com.scott.blog

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke

@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {  }
            formLogin {  }
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
}