package br.com.alura.forum.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Usuario
import java.util.Arrays
import br.com.alura.forum.service.TopicoService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import javax.validation.Valid
import org.springframework.web.bind.annotation.PutMapping
import br.com.alura.forum.dto.AtualizacaoTopicoForm
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.http.ResponseEntity
import br.com.alura.forum.mapper.TopicoViewMapper
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import javax.transaction.Transactional
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.data.web.PageableDefault
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Page
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.CacheEvict
import br.com.alura.forum.dto.TopicoPorCastecoriaDto
import io.swagger.v3.oas.annotations.security.SecurityRequirement

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {
	@GetMapping("/relatorio")
	fun relatorio(): List<TopicoPorCastecoriaDto>{
		return service.relatorio()
	}
	@GetMapping
	@Cacheable(cacheNames = ["topicos"], key ="#root.method.name")
	fun listar(
			@RequestParam(required = false) nomeCurso: String?,
			@PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
	): Page<TopicoView>{
		return service.listar(nomeCurso, paginacao)
	}
	
	@GetMapping("/{id}")
	fun buscarPorId(@PathVariable id: Long): TopicoView{
		return service.buscarPorId(id);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(cacheNames = ["topicos"], allEntries = true)
	fun cadastrar(@RequestBody @Valid form: NovoTopicoForm,
	uriBuilder: UriComponentsBuilder
	):ResponseEntity<TopicoView>{
		val topicoView = service.cadastrar(form)
		val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
		return ResponseEntity.created(uri).body(topicoView)
	}
	
	@PutMapping
	@Transactional
	@CacheEvict(cacheNames = ["topicos"], allEntries = true)
	fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm): ResponseEntity<TopicoView>{
		val topicoView = service.atualizar(form)
		return ResponseEntity.ok(topicoView)
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	@CacheEvict(cacheNames = ["topicos"], allEntries = true)
	fun deletar(@PathVariable id:Long){
		service.deletar(id)
	}
}