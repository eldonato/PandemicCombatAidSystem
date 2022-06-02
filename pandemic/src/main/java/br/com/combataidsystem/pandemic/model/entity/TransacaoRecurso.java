package br.com.combataidsystem.pandemic.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TransacaoRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "intercambio_id")
    @JsonIgnore
    private Intercambio intercambio;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Recurso recurso;

    public TransacaoRecurso(Intercambio intercambio, Recurso recurso) {
        this.intercambio = intercambio;
        this.recurso = recurso;
    }
}
