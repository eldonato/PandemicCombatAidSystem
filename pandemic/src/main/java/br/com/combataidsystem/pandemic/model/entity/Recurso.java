package br.com.combataidsystem.pandemic.model.entity;


import br.com.combataidsystem.pandemic.model.enums.TipoRecurso;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column
    public String nome;

    @Column
    public int tipoRecurso;

    @Column
    public int valor;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    @JsonIgnore
    public Hospital hospital;

    public Recurso(TipoRecurso tipoRecurso, Hospital hospital){
        this.tipoRecurso = tipoRecurso.getCodigo();
        this.nome = tipoRecurso.toString();
        this.valor = tipoRecurso.getPontos();
        this.hospital = hospital;
    }


}
