package br.com.combataidsystem.pandemic.model.DTO;

import br.com.combataidsystem.pandemic.model.entity.Recurso;
import lombok.*;

import java.util.List;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {

    private String nome;
    private String cnpj;
    private float percentualOcupacao;
    private String endereco;
    private List<Recurso> recursos;
    private String latitude;
    private String longitude;

}
