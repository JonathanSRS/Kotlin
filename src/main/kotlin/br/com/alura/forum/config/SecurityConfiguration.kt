package br.com.alura.forum.config

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.http.HttpMethod
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import br.com.alura.forum.security.JWTLoginfilter
import org.springframework.web.filter.OncePerRequestFilter
import br.com.alura.forum.security.JWTAuthenticationFilter
import org.springframework.context.annotation.Bean

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
		private val userDetailsService: UserDetailsService,
		private val jwtUtil: JWTUtil
):
	WebSecurityConfigurerAdapter() {
	override fun configure(http: HttpSecurity){
		http?.
		csrf()?.disable()?.
		authorizeRequests()?.
		antMatchers("/topicos")?.hasAuthority("LEITURA_ESCRITA")?.
		antMatchers("/respostas")?.hasAuthority("LEITURA_ESCRITA")?.
		antMatchers("/relatorios")?.hasAuthority("ADMIN")?.
		antMatchers(HttpMethod.GET,"/swagger-ui/*")?.permitAll()?.
		antMatchers(HttpMethod.GET,"/v3/api-docs/**")?.permitAll()?.
		antMatchers(HttpMethod.POST,"/login")?.permitAll()?.
		anyRequest()?.
		authenticated()?.
		and()
		http?.addFilterBefore(JWTLoginfilter(authManager = authenticationManager(), jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass)
		http?.addFilterBefore(JWTAuthenticationFilter(jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
		http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	}
	
	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
		return BCryptPasswordEncoder()
	}
	
	override fun configure(auth: AuthenticationManagerBuilder?){
		auth?.userDetailsService(userDetailsService)?.passwordEncoder(bCryptPasswordEncoder())
	}
}