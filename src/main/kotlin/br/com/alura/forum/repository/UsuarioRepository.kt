package br.com.alura.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import br.com.alura.forum.model.Usuario

interface UsuarioRepository: JpaRepository<Usuario, Long> {
	fun findByEmail(username: String?): Usuario
}
