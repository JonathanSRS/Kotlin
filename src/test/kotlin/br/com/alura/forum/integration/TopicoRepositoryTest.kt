package br.com.alura.forum.integration

import br.com.alura.forum.repository.TopicoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.junit.jupiter.Container
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.testcontainers.containers.MySQLContainer
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.DynamicPropertyRegistry
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import br.com.alura.forum.model.TopicoTest
import org.springframework.data.domain.PageRequest

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicoRepositoryTest {
	
	@Autowired
	private lateinit var topicoRepository: TopicoRepository
	private val paginacao = PageRequest.of(0,5)
	
	private val topico = TopicoTest.build()
	
	
	@Test
	fun `deve gerar um relatorio`(){
		topicoRepository.save(topico)
		val relatorio = topicoRepository.relatorio()
		
		assertThat(relatorio).isNotNull
//		assertThat(relatorio.first()).isExactlyInstanceOf(TopicosPorCategoriaDto::class.java)
	}
	
	 @Test
    fun `deve buscar um topico por nome`() {
        topicoRepository.save(topico)
        val resultado = topicoRepository.findByCursoNome(nomeCurso = "Kotlin", paginacao = paginacao)

        assertThat(resultado.totalElements).isEqualTo(1)
    }
	
}