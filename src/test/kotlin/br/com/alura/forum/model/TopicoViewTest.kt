package br.com.alura.forum.model

import br.com.alura.forum.dto.TopicoView
import java.time.LocalDateTime
import java.time.LocalDate

object TopicoViewTest {
	fun build() = TopicoView(
		id = 1,
		titulo = "Kotlin Basico",
		mensagem = "Aprendendo kotlin basico",
		status = StatusTopico.NAO_RESPONDIDO,
		dataCriacao = LocalDateTime.now(),
		dataAlteracao = LocalDate.now()
	)
}