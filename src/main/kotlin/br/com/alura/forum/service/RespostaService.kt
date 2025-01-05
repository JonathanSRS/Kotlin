package br.com.alura.forum.service

import org.springframework.stereotype.Service
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Curso
import java.util.Arrays
import br.com.alura.forum.dto.NovaRespostaForm
import java.util.stream.Collectors
import br.com.alura.forum.repository.RespostaRepository

@Service
class RespostaService (
	private val respostaRepository: RespostaRepository,
	private val emailService: EmailService
) {
	
//	init{
//		val resposta = Resposta(
//			id = 1,
//			mensagem = "",
//			autor = Usuario(
//					id = 4,
//						nome = "Pedro Rocha",
//						email = "pedro@email.com"
//			),
//			topico = Topico(
//					id = 1,
//				titulo = "Duvida Kotlin",
//				mensagem = "Variaveis no Kotlin",
//				curso = Curso(
//						id = 1,
//						nome = "Kotlin",
//						categoria = "Programacao"	
//						),
//				autor = Usuario(
//						id = 1,
//						nome = "Ana da Silva",
//						email = "ana@email.com"
//						)
//			),
//			solucao = true
//		)
//		
//		respostas = Arrays.asList(resposta);
//		
//	}
	
	fun salvar (resposta: Resposta) {		
		respostaRepository.save(resposta)
		val emailAutor = resposta.topico.autor.email
		emailService.notificar(emailAutor)
	}
	
//	fun cadastrar(form: NovaRespostaForm, idTopico: Long){
//		val resposta = respostaFormMapper.map(form)
//		resposta.id = resposta.size().toLong() +1
//		resposta.topico = topicoService.buscarPorId(idTopico)
//		respostas = respostas.plus(resposta)
//	}
}