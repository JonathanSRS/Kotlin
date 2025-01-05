package br.com.alura.forum.dto

import br.com.alura.forum.model.StatusTopico
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalDate

data class TopicoView (
		val id: Long?,
		val titulo: String,
		val mensagem: String,
		val status: StatusTopico,
		val dataCriacao: LocalDateTime,
		val dataAlteracao: LocalDate?
): Serializable