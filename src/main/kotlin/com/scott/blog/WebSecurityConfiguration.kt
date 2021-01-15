package com.scott.blog

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
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
                authorize("/user/**", hasAuthority("ROLE_EDITOR") )
                authorize(HttpMethod.GET, "/post", hasAnyAuthority("ROLE_EDITOR", "ROLE_AUTHOR"))
                authorize(HttpMethod.POST, "/post", hasAnyAuthority("ROLE_EDITOR", "ROLE_AUTHOR"))
                authorize(HttpMethod.DELETE, "/post/{\\d+}", hasAnyAuthority("ROLE_EDITOR"))
                authorize(HttpMethod.POST, "/post/{\\d+}/publish", hasAnyAuthority("ROLE_EDITOR"))
                authorize("/credential/**", hasAuthority("ROLE_EDITOR") )
                authorize("/profile/**", hasAuthority("ROLE_EDITOR") )
                authorize("/publication/**", hasAuthority("ROLE_EDITOR") )
                authorize("/**")
            }
            csrf { disable() }
//        http
//            ?.authorizeRequests()
//            ?.antMatchers("/user/**", "/post/**")
//            ?.permitAll()?.anyRequest()
//            ?.authenticated()
//            ?.and()
//            ?.formLogin()
//            ?.loginPage("/login")
//            ?.permitAll()
//            ?.and()
//            ?.logout()
//            ?.permitAll()
//            ?.and()
//            ?.httpBasic()

        }
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        auth?.inMemoryAuthentication()
            ?.withUser("scott")
            ?.password(encoder.encode("pw"))
            ?.roles("EDITOR")
            ?.and()
            ?.withUser("MrRobot")
            ?.password(encoder.encode("pw1"))
            ?.roles("AUTHOR")
            ?.and()
            ?.withUser("test_user_role_editor")
            ?.password(encoder.encode("pw"))
            ?.roles("EDITOR")
            ?.and()
            ?.withUser("test_user_role_author")
            ?.password(encoder.encode("pw"))
            ?.roles("AUTHOR")
            ?.and()
            ?.withUser("test_user_role_editor_and_author")
            ?.password(encoder.encode("pw"))
            ?.roles("EDITOR", "AUTHOR")
            ?.and()
            ?.withUser("test_user_role_no_access")
            ?.password(encoder.encode("pw"))
            ?.roles("NO_ACCESS")
    }
}