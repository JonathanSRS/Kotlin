package br.com.alura.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import br.com.alura.forum.model.Curso

interface CursoRepository: JpaRepository<Curso, Long> {
}