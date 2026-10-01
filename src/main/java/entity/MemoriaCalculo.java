package entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;


@Entity
public class MemoriaCalculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Integer mes;

    public BigDecimal saldoInicial;

    public BigDecimal juros;

    public BigDecimal saldoFinal;

    @ManyToOne
    @JsonIgnore
    public Simulacao simulacao;
}
