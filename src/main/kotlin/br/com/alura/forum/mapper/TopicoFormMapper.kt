package br.com.alura.forum.mapper

import org.springframework.stereotype.Component
import br.com.alura.forum.service.CursoService
import br.com.alura.forum.service.UsuarioService
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.model.Topico

@Component
class TopicoFormMapper(
		private val cursoService: CursoService,
		private val usuarioService: UsuarioService
	): Mapper<NovoTopicoForm, Topico>{
		override fun map(t: NovoTopicoForm):Topico{
			return Topico(
					titulo = t.titulo,
					mensagem = t.mensagem,
					curso = cursoService.buscaPorId(t.idCurso),
					autor = usuarioService.buscaPorId(t.idAutor)
			)
			
		}
	
}