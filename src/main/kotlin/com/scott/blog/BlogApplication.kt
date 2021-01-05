package com.scott.blog

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Blog Application", description = "This is my blog app"))
class BlogApplication

fun main(args: Array<String>) {

	runApplication<BlogApplication>(*args)
	println("Hello Blogger!")

}

//fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
//	OpenApiOptions(
//		Info().apply {
//			version("1.0")
//			description("User API")
//		}
//	).apply {
//		path("/swagger-docs") // endpoint for OpenAPI json
//		swagger(SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
//		reDoc(ReDocOptions("/redoc")) // endpoint for redoc
//		defaultDocumentation { doc ->
//			doc.json("500", ErrorResponse::class.java)
//			doc.json("503", ErrorResponse::class.java)
//		}
//	}
//)
