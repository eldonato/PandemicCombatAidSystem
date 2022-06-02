package br.com.combataidsystem.pandemic.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Intercambio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime dataIntercambio;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "hospital_origem_id")
    private Hospital hospitalOrigem;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "hospital_destino_id")
    private Hospital hospitalDestino;

    @OneToMany(mappedBy = "intercambio")
    private List<TransacaoRecurso> transacaoRecursos;

    public Intercambio(Hospital hospitalOrigem, Hospital hospitalDestino){
        this.hospitalOrigem = hospitalOrigem;
        this.hospitalDestino = hospitalDestino;
    }

}
