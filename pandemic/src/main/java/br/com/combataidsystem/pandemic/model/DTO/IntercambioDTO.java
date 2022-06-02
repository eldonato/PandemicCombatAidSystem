package br.com.combataidsystem.pandemic.model.DTO;

import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.model.entity.Recurso;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntercambioDTO {

    private Long hospitalOrigemId;
    private Long hospitalDestinoId;
    private Hospital hospitalOrigem;
    private Hospital hospitalDestino;
    private List<Recurso> recursosOrigem;
    private List<Recurso> recursosDestino;


}
