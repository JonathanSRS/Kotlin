package br.com.alura.forum.config

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import io.jsonwebtoken.Jwts
import java.util.*
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import br.com.alura.forum.service.UsuarioService

@Component
class JWTUtil(
		private val usuarioService: UsuarioService
) {
	private val expiration : Long = 60000
	
	@Value("\${jwt.secret}")
	private lateinit var secret : String
	
	fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String?{
		return Jwts.builder()
				.setSubject(username)
				.claim("role", authorities)
				.setExpiration(Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.toByteArray())
				.compact()
	}
	
	fun isValid(jwt: String?):Boolean{
		return try{
			Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
			true
		}catch(e: IllegalArgumentException){
			false
			
		}
	}
	
	fun getAuthentication(jwt: String?) : Authentication{
		val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
		val user = usuarioService.loadUserByUsername(username)
		return UsernamePasswordAuthenticationToken(username, null, user.authorities)
	}
}