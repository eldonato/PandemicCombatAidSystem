package br.com.combataidsystem.pandemic.repository;

import br.com.combataidsystem.pandemic.model.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
