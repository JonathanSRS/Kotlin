package br.com.alura.forum.security

import br.com.alura.forum.config.JWTUtil
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.FilterChain
import org.springframework.security.core.context.SecurityContextHolder

class JWTAuthenticationFilter (private val jwtUtil: JWTUtil): OncePerRequestFilter(){
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain):Unit {
		val token = request.getHeader("Authorization")
		val jwt = getTokenDetail(token)
		if(jwtUtil.isValid(jwt)){
			val authentication = jwtUtil.getAuthentication(jwt)
			SecurityContextHolder.getContext().authentication = authentication
		}
		filterChain.doFilter(request, response)
	}
	
	private fun getTokenDetail(token: String?): String?{
		return token?.let { jwt ->
			jwt.startsWith("Bearer ")
			jwt.substring(7, jwt.length)
		}
	}
}