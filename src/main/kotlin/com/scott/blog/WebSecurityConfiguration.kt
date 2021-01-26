package com.scott.blog

import com.scott.blog.service.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import java.lang.Exception
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var customAuthenticationEntryPoint : CustomAuthenticationEntryPoint

    @Autowired
    lateinit var jpaAuthenticationProvider: JPAAuthenticationProvider

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        if (http != null) {
            http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/**").hasRole("Editor")
                .antMatchers(HttpMethod.GET, "/post").hasAnyRole("Editor", "Author")
                .antMatchers(HttpMethod.POST, "/post").hasAnyRole("Editor", "Author")
                .antMatchers(HttpMethod.DELETE, "/post/{\\d+}").hasRole("Editor")
                .antMatchers(HttpMethod.POST, "/post/{\\d+}/publish").hasRole("Editor")
                .antMatchers("/credential/**").hasRole("Editor")
                .antMatchers("/publication/**").hasRole("Editor")
                .antMatchers("/profile/**").hasRole("Editor")
                .antMatchers("/role").permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .csrf().disable()
                .httpBasic()
        }
//OLD STUFF
//        http?.httpBasic()?.authorizeRequests()?.anyRequest()?.authenticated()?.and()

//        http {
//            httpBasic { authenticationEntryPoint = customAuthenticationEntryPoint }
////            formLogin {  }
//            authorizeRequests {
//                authorize(HttpMethod.GET,"/user/**", hasAuthority("EDITOR"))//"ROLE_EDITOR") )
////                authorize("/user/**", hasAuthority("ROLE_EDITOR") )
//                authorize(HttpMethod.GET, "/post", hasAnyAuthority("ROLE_EDITOR", "ROLE_AUTHOR"))
//                authorize(HttpMethod.POST, "/post", hasAnyAuthority("ROLE_EDITOR", "ROLE_AUTHOR"))
//                authorize(HttpMethod.DELETE, "/post/{\\d+}", hasAnyAuthority("ROLE_EDITOR"))
//                authorize(HttpMethod.POST, "/post/{\\d+}/publish", hasAnyAuthority("ROLE_EDITOR"))
//                authorize("/credential/**", hasAuthority("ROLE_EDITOR") )
//                authorize("/profile/**", hasAuthority("ROLE_EDITOR") )
////                authorize("/publication/**", hasAuthority("ROLE_EDITOR") )
//                authorize("/role", permitAll)
////                authorize("/**")
//            }
//            csrf { disable() }
// This is from the demo and they didnt have any red squigly lines           http.httpBasic().authorizeRequests().anyRequest().authenticated().and()
// The demo she added logout logout requestmatcher(AntPathRequestMatcher("/logout")).logoutsessussurl("/login").invalidatehttpsession(true).deletecookie(jssessonid) something like that

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

//        }
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(jpaAuthenticationProvider)// demo used .passwordencoder(encoder())
//        auth?.userDetailsService(MyUserDetailsService())// demo used .passwordencoder(encoder())

        //THIS IS REMOVED FOR TASK 9
//        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
//        auth?.inMemoryAuthentication()
//            ?.withUser("scott")
//            ?.password(encoder.encode("pw"))
//            ?.roles("EDITOR")
//            ?.and()
//            ?.withUser("MrRobot")
//            ?.password(encoder.encode("pw1"))
//            ?.roles("AUTHOR")
//            ?.and()
//            ?.withUser("test_user_role_editor")
//            ?.password(encoder.encode("pw"))
//            ?.roles("EDITOR")
//            ?.and()
//            ?.withUser("test_user_role_author")
//            ?.password(encoder.encode("pw"))
//            ?.roles("AUTHOR")
//            ?.and()
//            ?.withUser("test_user_role_editor_and_author")
//            ?.password(encoder.encode("pw"))
//            ?.roles("EDITOR", "AUTHOR")
//            ?.and()
//            ?.withUser("test_user_role_no_access")
//            ?.password(encoder.encode("pw"))
//            ?.roles("NO_ACCESS")
    }
}