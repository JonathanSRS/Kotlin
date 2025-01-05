package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import java.util.Arrays
import org.springframework.stereotype.Service
import br.com.alura.forum.repository.CursoRepository

@Service
class CursoService (val repository: CursoRepository) {
	
		fun buscaPorId(id: Long): Curso{
			return repository.getOne(id)
	}
	
}