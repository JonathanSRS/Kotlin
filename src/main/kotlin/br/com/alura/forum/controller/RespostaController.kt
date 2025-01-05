package br.com.alura.forum.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import br.com.alura.forum.model.Resposta
import org.springframework.web.bind.annotation.GetMapping
import br.com.alura.forum.service.RespostaService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import br.com.alura.forum.dto.NovaRespostaForm
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/resposta")
class RespostaController (private val service: RespostaService){
	
	@PostMapping
	fun salvar(@RequestBody @Valid resposta: Resposta) = service.salvar(resposta)
	
//	fun cadastrar(@PathVariable id: Long, @RequestBody @Valid dto: NovaRespostaForm){
//		service.cadastrar(dto, id)
//	}
}