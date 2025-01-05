package br.com.alura.forum.model

import java.time.LocalDateTime
import java.util.ArrayList
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Enumerated
import javax.persistence.EnumType
import javax.persistence.OneToMany
import javax.persistence.ManyToOne
import java.time.LocalDate

@Entity
data class Topico (
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long? = null,
		var titulo: String,
		var mensagem: String,
		val dataCriacao: LocalDateTime = LocalDateTime.now(),
		var dataAlteracao: LocalDate? = null,
		@ManyToOne
		val curso: Curso,
		@ManyToOne
		val autor: Usuario,
		@Enumerated(value = EnumType.STRING)
		val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
		@OneToMany(mappedBy = "topico")
		val respostas: List<Resposta> = ArrayList()
)
