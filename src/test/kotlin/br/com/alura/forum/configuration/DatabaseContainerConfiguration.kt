package br.com.alura.forum.configuration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.containers.GenericContainer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class DatabaseContainerConfiguration {
	companion object{
		@Container
		private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.28").apply{
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
			registry.add("spring.redis.host", redisContainer::getFirstMappedPort)
			
		}
		
	}
}