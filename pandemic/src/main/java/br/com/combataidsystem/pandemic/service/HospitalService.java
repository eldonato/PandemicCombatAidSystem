package br.com.combataidsystem.pandemic.service;

import br.com.combataidsystem.pandemic.model.DTO.HospitalDTO;
import br.com.combataidsystem.pandemic.model.DTO.MediaRecursosDTO;
import br.com.combataidsystem.pandemic.model.DTO.RelatorioDTO;
import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.model.entity.Intercambio;
import br.com.combataidsystem.pandemic.model.entity.Recurso;
import br.com.combataidsystem.pandemic.model.enums.TipoRecurso;
import br.com.combataidsystem.pandemic.repository.HospitalRepository;
import br.com.combataidsystem.pandemic.repository.IntercambioRepository;
import br.com.combataidsystem.pandemic.repository.RecursoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final RecursoRepository recursoRepository;
    private final IntercambioRepository intercambioRepository;

    public HospitalService(HospitalRepository hospitalRepository, RecursoRepository recursoRepository, IntercambioRepository intercambioRepository) {
        this.hospitalRepository = hospitalRepository;
        this.recursoRepository = recursoRepository;
        this.intercambioRepository = intercambioRepository;
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

    public RelatorioDTO gerarRelatorio(){
        List<Hospital> hospitais = hospitalRepository.findAll();

        int numeroDeHospitais = hospitais.size();

        long qtdHospitaisSublotados = hospitais.stream()
                .filter(hospital -> hospital.getPercentualOcupacao() < 90).count();
        long qtdHospitaisSuperlotados = numeroDeHospitais - qtdHospitaisSublotados;

        double porcentagemHospitaisSublotados = (qtdHospitaisSublotados * 100f) / numeroDeHospitais;
        double porcentagemHospitaisSuperlotados = (qtdHospitaisSuperlotados * 100f) / numeroDeHospitais;

        Hospital hospitalSublotadoMaisTempo = hospitais.stream()
                .filter(hospital -> hospital.getPercentualOcupacao() <= 90)
                .min(Comparator.comparing(Hospital::getDataAttPercentual))
                .orElse(null);
        Hospital hospitalSuperlotadoMaisTempo = hospitais.stream()
                .filter(hospital -> hospital.getPercentualOcupacao() > 90)
                .min(Comparator.comparing(Hospital::getDataAttPercentual))
                .orElse(null);

        List<Recurso> recursos = recursoRepository.findAll();

        Map<Integer, Long> frequenciaMap = recursos.stream()
                .collect(Collectors.groupingBy(Recurso::getTipoRecurso, Collectors.counting()));

        List<Intercambio> intercambios = intercambioRepository.findAll();

        List<MediaRecursosDTO> mediaRecursos = frequenciaMap.entrySet().stream()
                .map(entry -> new MediaRecursosDTO(TipoRecurso.getTipoRecurso(entry.getKey()), entry.getValue().doubleValue() / numeroDeHospitais))
                .collect(Collectors.toList());

        return new RelatorioDTO(porcentagemHospitaisSuperlotados, porcentagemHospitaisSublotados,
                hospitalSuperlotadoMaisTempo, hospitalSublotadoMaisTempo,
                mediaRecursos, intercambios);
    }
}
