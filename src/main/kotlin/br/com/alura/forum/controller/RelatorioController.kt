package br.com.alura.forum.controller

import br.com.alura.forum.service.TopicoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
@RequestMapping("relatorios")
class RelatorioController (
		private val topicoService: TopicoService
){
	@GetMapping
	fun relatorio(model: Model):String{
		model.addAttribute("topicosPorCategorias", topicoService.relatorio())
		return "relatorio"
	}
}