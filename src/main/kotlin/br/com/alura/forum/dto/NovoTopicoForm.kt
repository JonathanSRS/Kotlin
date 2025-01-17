package br.com.alura.forum.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class NovoTopicoForm (
	@field:NotEmpty(message = "Titulo nao pode ser branco")
	@field:Size(min = 5, max = 100, message="Titulo deve ter entre 5 e 100 caracteres")
	val titulo: String,
	@NotEmpty(message = "Messagem nao pode ser em branco")
	val mensagem: String,
	@field:NotNull
	val idCurso: Long,
	@field:NotNull
	val idAutor: Long
)