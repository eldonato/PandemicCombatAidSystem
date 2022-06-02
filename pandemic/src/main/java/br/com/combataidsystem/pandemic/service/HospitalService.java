package br.com.combataidsystem.pandemic.service;

import br.com.combataidsystem.pandemic.model.DTO.HospitalDTO;
import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.model.entity.Recurso;
import br.com.combataidsystem.pandemic.model.enums.TipoRecurso;
import br.com.combataidsystem.pandemic.repository.HospitalRepository;
import br.com.combataidsystem.pandemic.repository.RecursoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final RecursoRepository recursoRepository;

    public HospitalService(HospitalRepository hospitalRepository, RecursoRepository recursoRepository) {
        this.hospitalRepository = hospitalRepository;
        this.recursoRepository = recursoRepository;
    }

    public Hospital salvar(HospitalDTO hospitalDTO){

        Hospital hospital = new Hospital(hospitalDTO.getNome(), hospitalDTO.getCnpj(), hospitalDTO.getPercentualOcupacao(),
                hospitalDTO.getEndereco(), hospitalDTO.getLatitude(), hospitalDTO.getLongitude(), hospitalDTO.getRecursos());
        hospital.setDataAttPercentual(LocalDateTime.now());

        List<Recurso> recursos = hospital.getRecursos().stream()
                .map(recurso -> new Recurso(TipoRecurso.getTipoRecurso(recurso.getTipoRecurso()), hospital))
                .toList();

        hospital.setRecursos(recursos);
        hospitalRepository.save(hospital);
        recursoRepository.saveAll(recursos);

        return hospital;
    }

    public List<Hospital> getAll(){
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getById(Long id){
        return hospitalRepository.findById(id);
    }

    public void deleteById(Long id){
        hospitalRepository.deleteById(id);
    }

    public void atualizarOcupacao(Hospital hospital, float percentualOcupacao){
        hospital.setPercentualOcupacao(percentualOcupacao);
        hospital.setDataAttPercentual(LocalDateTime.now());
        hospitalRepository.save(hospital);
    }

}
