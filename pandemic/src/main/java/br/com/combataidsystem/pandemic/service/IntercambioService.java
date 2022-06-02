package br.com.combataidsystem.pandemic.service;

import br.com.combataidsystem.pandemic.model.DTO.IntercambioDTO;
import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.model.entity.Intercambio;
import br.com.combataidsystem.pandemic.model.entity.Recurso;
import br.com.combataidsystem.pandemic.model.entity.TransacaoRecurso;
import br.com.combataidsystem.pandemic.repository.HospitalRepository;
import br.com.combataidsystem.pandemic.repository.IntercambioRepository;
import br.com.combataidsystem.pandemic.repository.RecursoRepository;
import br.com.combataidsystem.pandemic.repository.TransacaoRecursoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class IntercambioService {


    private final IntercambioRepository repository;
    private final RecursoRepository recursoRepository;
    private final TransacaoRecursoRepository trRepository;
    private final HospitalRepository hospitalRepository;


    public IntercambioService(IntercambioRepository repository, RecursoRepository recursoRepository,
                              TransacaoRecursoRepository trRepository, HospitalRepository hospitalRepository) {
        this.repository = repository;
        this.recursoRepository = recursoRepository;
        this.trRepository = trRepository;
        this.hospitalRepository = hospitalRepository;
    }

    public boolean verificarPontos(IntercambioDTO intercambioDTO){
        int somaOrigem = intercambioDTO.getRecursosOrigem().stream().mapToInt(Recurso::getValor).sum();
        int somaDestino = intercambioDTO.getRecursosDestino().stream().mapToInt(Recurso::getValor).sum();

        return somaOrigem == somaDestino;
    }

    public boolean validarRecursos(IntercambioDTO intercambioDTO){
        return (intercambioDTO.getHospitalOrigem().getRecursos().containsAll(intercambioDTO.getRecursosOrigem())
                &&
                intercambioDTO.getHospitalDestino().getRecursos().containsAll(intercambioDTO.getRecursosDestino()));
    }

    public boolean validarIntercambio(IntercambioDTO intercambioDTO){

        //se a ocupação em qualquer um dos hospitais for maior que 90%
        //não será necessário checar se os pontos são equivalentes
        if (intercambioDTO.getHospitalOrigem().getPercentualOcupacao() > 90
                || intercambioDTO.getHospitalDestino().getPercentualOcupacao() > 90){
            return true;
        }

        if (verificarPontos(intercambioDTO))
            return true;

        return false;
    }

    public void realizarIntercambio(IntercambioDTO intercambioDTO){

        Hospital hospitalOrigem = hospitalRepository.findById(intercambioDTO.getHospitalOrigem().getId())
                .orElseThrow();
        Hospital hospitalDestino = hospitalRepository.findById(intercambioDTO.getHospitalDestino().getId())
                .orElseThrow();

        List<Recurso> recursosOrigem = recursoRepository.findAll().stream()
                .filter(recurso -> Objects.equals(recurso.getHospital().getId(), hospitalOrigem.getId()))
                .toList();

        List<Recurso> recursosDestino = recursoRepository.findAll().stream()
                .filter(recurso -> Objects.equals(recurso.getHospital().getId(), hospitalDestino.getId()))
                .toList();

        Intercambio intercambioOrigem = new Intercambio(hospitalOrigem, hospitalDestino);
        Intercambio intercambioDestino = new Intercambio(hospitalDestino, hospitalOrigem);

        List<TransacaoRecurso> transacoesOrigem = recursosOrigem.stream()
                .map(recurso -> new TransacaoRecurso(intercambioOrigem, recurso)).toList();

        List<TransacaoRecurso> transacoesDestino = recursosDestino.stream()
                .map(recurso -> new TransacaoRecurso(intercambioDestino, recurso)).toList();

        intercambioOrigem.setDataIntercambio(LocalDateTime.now());
        intercambioOrigem.setTransacaoRecursos(transacoesOrigem);

        intercambioDestino.setDataIntercambio(LocalDateTime.now());
        intercambioDestino.setTransacaoRecursos(transacoesDestino);

        repository.save(intercambioOrigem);
        repository.save(intercambioDestino);

        trRepository.saveAll(transacoesOrigem);
        trRepository.saveAll(transacoesDestino);

        for(Recurso recurso : recursosOrigem){
            recurso.setHospital(hospitalDestino);
            System.out.println(recurso.getNome() +" "+recurso.getHospital().getId());
        }
        for(Recurso recurso : recursosDestino){
            recurso.setHospital(hospitalOrigem);
        }

    }
}
