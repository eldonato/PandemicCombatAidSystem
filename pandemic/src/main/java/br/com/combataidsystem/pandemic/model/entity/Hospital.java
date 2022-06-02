package br.com.combataidsystem.pandemic.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Hospital implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cnpj;
    private float percentualOcupacao;
    private LocalDateTime dataAttPercentual;
    private String endereco;
    private String latitude;
    private String longitude;

    @OneToMany(mappedBy = "hospital")
    private List<Recurso> recursos;

    public Hospital(String nome, String cnpj, float percentualOcupacao, String endereco, String latitude, String longitude, List<Recurso> recursos) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.percentualOcupacao = percentualOcupacao;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recursos = recursos;
    }
}
