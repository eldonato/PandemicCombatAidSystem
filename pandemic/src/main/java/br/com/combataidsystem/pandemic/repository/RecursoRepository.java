package br.com.combataidsystem.pandemic.repository;

import br.com.combataidsystem.pandemic.model.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}
