package br.com.alura.forum.service

import io.mockk.*
import org.springframework.data.domain.PageImpl
import br.com.alura.forum.model.TopicoTest
import org.springframework.data.domain.Pageable
import br.com.alura.forum.repository.TopicoRepository
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoViewTest
import org.junit.jupiter.api.Test
import java.util.*
import br.com.alura.forum.exception.NotFoundException
import org.junit.jupiter.api.assertThrows
import org.assertj.core.api.Assertions.assertThat

class TopicoServiceTest {
	private val topico = TopicoTest.build()
	private val pageable: Pageable = mockk()
	val topicos = PageImpl(listOf(topico))
	
	val topicoRepository: TopicoRepository = mockk{
		every { findByCursoNome(any(), any())} returns topicos
		every { findAll(pageable) } returns topicos
	}
	
	val topicoFormMapper: TopicoFormMapper = mockk()
	val topicoViewMapper: TopicoViewMapper = mockk{
		every {map(any())} returns TopicoViewTest.build()
	}
	
	val topicoService = TopicoService(
		topicoRepository, topicoViewMapper, topicoFormMapper
	)
	
	@Test
	fun `deve listar topicos a partir do nome do curso`(){
		topicoService.listar("Kotlin Basico", pageable)
		
		verify(exactly = 1){ topicoRepository.findByCursoNome(any(), any())}
		verify(exactly = 1){ topicoViewMapper.map(any())}
		verify(exactly = 0){ topicoRepository.findAll(pageable)}
	}
	
	@Test
	fun `deve listar todos os topicos quando nome do curso for nulo`(){
		topicoService.listar(null, pageable)
		
		verify(exactly = 0){ topicoRepository.findByCursoNome(any(), any())}
		verify(exactly = 1){ topicoViewMapper.map(any())}
		verify(exactly = 1){ topicoRepository.findAll(pageable)}
	}
	
	@Test
	fun `deve listar not found exception quando topico nao for achado`(){
		every {topicoRepository.findById(any()) } returns Optional.empty()
		
		val atual = assertThrows<NotFoundException>{
			topicoService.buscarPorId(1)
		}
		
		assertThat(atual.message).isEqualTo("Topico nao encontrado!")
	}
}