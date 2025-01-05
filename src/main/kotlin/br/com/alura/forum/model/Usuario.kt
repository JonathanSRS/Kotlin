package br.com.alura.forum.model
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.FetchType
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
data class Usuario (
		@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
		val id: Long? = null,
		val nome: String,
		val email: String,
		val password: String,
		
		@JsonIgnore
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinColumn(name = "usuario_role")
		val role: List<Role> = mutableListOf()
)
