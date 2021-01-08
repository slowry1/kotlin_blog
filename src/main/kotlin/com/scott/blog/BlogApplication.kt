package com.scott.blog

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Blog Application", description = "This is my blog app"))
class BlogApplication

fun main(args: Array<String>) {

	runApplication<BlogApplication>(*args)
//	{
//		beans {
//			bean {
//				fun user(user: String, pw: String, vararg roles: String) =
//					User.withDefaultPasswordEncoder().username(user).password(pw).roles(*roles).build()
//				InMemoryUserDetailsManager(
//					user("scott", "pw", "USER"),
//					user("MrRobot", "pw1", "ADMIN")
//				)
//			}
//		}
//	}
	println("Hello Blogger!")

}
