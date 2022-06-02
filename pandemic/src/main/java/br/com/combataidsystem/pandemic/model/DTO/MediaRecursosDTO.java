package br.com.combataidsystem.pandemic.model.DTO;

import br.com.combataidsystem.pandemic.model.enums.TipoRecurso;
import lombok.Getter;
import lombok.Setter;

public class MediaRecursosDTO {

    private Integer tipoRecurso;
    @Getter
    @Setter
    private Double quantidade;

    public MediaRecursosDTO(TipoRecurso tipoRecurso, Double quantidade) {
        setTipoRecurso(tipoRecurso);
        this.quantidade = quantidade;
    }

    public TipoRecurso getTipoRecurso() {
        return TipoRecurso.getTipoRecurso(tipoRecurso);
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        if (tipoRecurso != null) {
            this.tipoRecurso = tipoRecurso.getCodigo();
        }
    }
}
