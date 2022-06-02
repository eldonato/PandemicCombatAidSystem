package br.com.combataidsystem.pandemic.http.controller;

import br.com.combataidsystem.pandemic.model.DTO.HospitalDTO;
import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.service.HospitalService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService service;
    private final ModelMapper mapper;

    public HospitalController(HospitalService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hospital salvar(@RequestBody HospitalDTO hospital){
        return service.salvar(hospital);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Hospital> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Hospital getById(@PathVariable("id") Long id){
        return service.getById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital não encontrado"));


    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        service.getById(id)
                .map(hospital -> {
                    service.deleteById(hospital.getId());
                    return Void.TYPE;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital não encontrado"));
    }

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Hospital update(@PathVariable("id") Long id, @RequestBody Hospital hospital){
//        return service.getById(id)
//                .map(hospitalRepo -> {
//                    mapper.map(hospital, hospitalRepo);
//                    service.salvar(hospitalRepo);
//                    return hospitalRepo;
//                })
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital não encontrado"));
//    }

    @PutMapping("/{id}/atualizarOcupacao")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarOcupacao(@RequestParam float percentualOcupacao, @PathVariable Long id){
        var hospitalBase = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital não encontrado"));

        service.atualizarOcupacao(hospitalBase, percentualOcupacao);
    }


}
