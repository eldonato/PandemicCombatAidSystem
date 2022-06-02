package br.com.combataidsystem.pandemic.http.controller;

import br.com.combataidsystem.pandemic.model.DTO.IntercambioDTO;
import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.model.entity.Recurso;
import br.com.combataidsystem.pandemic.service.HospitalService;
import br.com.combataidsystem.pandemic.service.IntercambioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/intercambio")
public class IntercambioController {

    private final IntercambioService service;
    private final HospitalService hospitalService;
    private final ModelMapper mapper;

    public IntercambioController(IntercambioService service, ModelMapper mapper, HospitalService hospitalService) {
        this.service = service;
        this.mapper = mapper;
        this.hospitalService = hospitalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void RealizarIntercambio(@RequestBody IntercambioDTO intercambioDTO){

        Hospital hospitalOrigem = hospitalService.getById(intercambioDTO.getHospitalOrigemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Hospital hospitalDestino = hospitalService.getById(intercambioDTO.getHospitalDestinoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        intercambioDTO.setHospitalOrigem(hospitalOrigem);
        intercambioDTO.setHospitalDestino(hospitalDestino);

        if(!service.validarIntercambio(intercambioDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um dos hospitais não está oferecendo materiais o suficiente para que esse intercâmbio seja feito com sucesso!");
        }

        service.realizarIntercambio(intercambioDTO);
    }


}
