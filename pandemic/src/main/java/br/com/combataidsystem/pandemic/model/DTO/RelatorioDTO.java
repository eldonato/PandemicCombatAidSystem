package br.com.combataidsystem.pandemic.model.DTO;

import br.com.combataidsystem.pandemic.model.entity.Hospital;
import br.com.combataidsystem.pandemic.model.entity.Intercambio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDTO {
    private Double porcentagemHospitaisSuperlotados;
    private Double porcentagemHospitaisSublotados;
    private Hospital hospitalSuperlotadoMaisTempo;
    private Hospital hospitalSublotadoMaisTempo;
    private List<MediaRecursosDTO> mediaRecursos;
    private List<Intercambio> historicoIntercambios;

}
