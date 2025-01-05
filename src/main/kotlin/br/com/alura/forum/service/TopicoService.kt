package br.com.alura.forum.service

import org.springframework.stereotype.Service
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import java.util.Arrays
import br.com.alura.forum.model.Curso
import java.util.ArrayList
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.dto.TopicoView
import java.util.stream.Collectors
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import br.com.alura.forum.dto.TopicoPorCastecoriaDto
import java.time.LocalDate

@Service
class TopicoService(
	private val repository: TopicoRepository,
	private val topicoViewMapper: TopicoViewMapper,
	private val topicoFormMapper: TopicoFormMapper,
	private val notFoundMessage: String = "Topico nao encontrado!"
		) {
	
//	init{
//		val topico = Topico(
//				id = 1,
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
//				
//				)
//				val topico2 = Topico(
//						id = 2,
//						titulo = "Duvida Kotlin 2",
//						mensagem = "Variaveis no Kotlin",
//						curso = Curso(
//								id = 1,
//								nome = "Kotlin",
//								categoria = "Programacao"	
//								),
//						autor = Usuario(
//								id = 1,
//								nome = "Ana da Silva",
//								email = "ana@email.com"
//								)
//						
//						)
//				val topico3 = Topico(
//						id = 3,
//						titulo = "Duvida Kotlin 3",
//						mensagem = "Variaveis no Kotlin",
//						curso = Curso(
//								id = 1,
//								nome = "Kotlin",
//								categoria = "Programacao"	
//								),
//						autor = Usuario(
//								id = 1,
//								nome = "Ana da Silva",
//								email = "ana@email.com"
//								)
//						
//						)
//		
//		topicos = Arrays.asList(topico, topico2, topico3)
//	}
	fun relatorio(): List<TopicoPorCastecoriaDto>{
		return repository.relatorio()
	}
	fun listar(
			nomeCurso: String?,
			paginacao: Pageable
	): Page<TopicoView>{
		val topicos = if(nomeCurso == null){
			repository.findAll(paginacao)
		}else{
			repository.findByCursoNome(nomeCurso, paginacao)
		}
		return topicos.map{
			t -> topicoViewMapper.map(t)
		}
	}
	
	fun buscarPorId(id: Long): TopicoView{
		val topico = repository.findById(id)
			.orElseThrow{NotFoundException(notFoundMessage)}
		return topicoViewMapper.map(topico)
	}
	
	fun cadastrar(form: NovoTopicoForm): TopicoView{
			val topico = topicoFormMapper.map(form)
			repository.save(topico)
			return topicoViewMapper.map(topico)
	}
	
	fun atualizar(form: AtualizacaoTopicoForm): TopicoView{
		val topico = repository.findById(form.id)
			.orElseThrow{NotFoundException(notFoundMessage)}
		topico.titulo = form.titulo
		topico.mensagem = form.mensagem
		topico.dataAlteracao = LocalDate.now()
		return topicoViewMapper.map(topico)
	}
	
	fun deletar(id: Long){
		return repository.deleteById(id)
	}
}