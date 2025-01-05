package br.com.alura.forum.security

import org.springframework.security.authentication.AuthenticationManager
import br.com.alura.forum.config.JWTUtil
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.fasterxml.jackson.databind.ObjectMapper
import br.com.alura.forum.model.Credentials
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.servlet.FilterChain
import org.springframework.security.core.userdetails.UserDetails

class JWTLoginfilter(
		private val authManager: AuthenticationManager,
		private val jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {
	override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?) : Authentication{
		val(username, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java)
		val token = UsernamePasswordAuthenticationToken(username, password)
		return authManager.authenticate(token)
	}
	
	override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication){
		val user = (authResult?.principal as UserDetails)
		val token = jwtUtil.generateToken(user.username, user.authorities)
		response?.addHeader("Authorization", "Bearer $token")
	}
}