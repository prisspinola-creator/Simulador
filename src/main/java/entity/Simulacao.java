package entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import entity.MemoriaCalculo;

@Entity
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public BigDecimal valorInicial;

    public BigDecimal taxaJurosMensal;

    public Integer prazoMeses;

    public BigDecimal valorTotalFinal;

    public BigDecimal valorTotalJuros;

    @OneToMany(mappedBy = "simulacao", cascade = CascadeType.ALL)
    public List<MemoriaCalculo> memoria;
}
