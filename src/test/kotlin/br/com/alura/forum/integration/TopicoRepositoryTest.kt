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
import org.testcontainers.containers.GenericContainer

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicoRepositoryTest {
	
	@Autowired
	private lateinit var topicoRepository: TopicoRepository
	private val paginacao = PageRequest.of(0,5)
	
	private val topico = TopicoTest.build()
	
	companion object{
		@Container
		private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.29").apply{
			withDatabaseName("testedb")
			withUsername("jon")
			withPassword("123456")
		}
		
		@Container
		private val redisContainer = GenericContainer<Nothing>("redis:latest").apply{
			withExposedPorts(6379)
		}
		
		@JvmStatic
		@DynamicPropertySource
		fun properties(registry: DynamicPropertyRegistry){
			registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
			registry.add("spring.datasource.username", mysqlContainer::getUsername);
			registry.add("spring.datasource.password", mysqlContainer::getPassword);

			registry.add("spring.redis.host", redisContainer::getContainerIpAddress)
			registry.add("spring.redis.port", redisContainer::getFirstMappedPort)
			
		}
		
	}
	
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