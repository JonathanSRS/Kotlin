package br.com.alura.forum.config

import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@Configuration
@OpenAPIDefinition(info = Info(title = "API's do fórum Alura"))
@SecurityScheme(
	name= "bearerAuth",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "jwt",
	scheme = "bearer"
)
class SwaggerConfig