package br.com.alura.forum.service

import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service
import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails

@Service
class UsuarioService (val repository: UsuarioRepository): UserDetailsService{
	fun buscaPorId(id : Long): Usuario{
		return repository.getOne(id)
	}
	
	override fun loadUserByUsername(username: String?): UserDetails{
		val usuario = repository.findByEmail(username) ?: throw RuntimeException()
		return UserDetail(usuario);
	}
}