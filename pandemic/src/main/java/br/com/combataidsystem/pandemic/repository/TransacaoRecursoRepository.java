package br.com.combataidsystem.pandemic.repository;

import br.com.combataidsystem.pandemic.model.entity.TransacaoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRecursoRepository extends JpaRepository<TransacaoRecurso, Long> {
}
