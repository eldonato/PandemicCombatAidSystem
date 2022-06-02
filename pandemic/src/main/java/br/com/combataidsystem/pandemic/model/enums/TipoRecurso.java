package br.com.combataidsystem.pandemic.model.enums;

import lombok.Getter;

import java.util.Arrays;

public enum TipoRecurso {

    MEDICO(1,3), ENFERMEIRO(2,3), RESPIRADOR(3,5),
    TOMOGRAFO(4,12), AMBULANCIA(5,10);

    @Getter private final int pontos;
    @Getter private final int codigo;

    TipoRecurso(int codigo, int pontos){
        this.codigo = codigo;
        this.pontos = pontos;
    }

    public static TipoRecurso getTipoRecurso(int codigo){
        return Arrays.stream(values())
                .filter(tr -> tr.codigo==codigo).findFirst().orElse(null);
    }
}