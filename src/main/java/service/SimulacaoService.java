package service;

import entity.MemoriaCalculo;
import entity.Simulacao;
import repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    SimulacaoRepository repository;

    @Transactional
    public Simulacao calcularSimulacao(Simulacao simulacao){

        List<MemoriaCalculo> memoria = new ArrayList<>();

        // Inicializar saldo com duas casas decimais
        BigDecimal saldo = simulacao.valorInicial.setScale(2, RoundingMode.HALF_UP);

        // Calcular taxa mensal a partir do percentual, usando 0.01 (passo A)
        BigDecimal taxa = simulacao.taxaJurosMensal.multiply(new BigDecimal("0.01"));

        // Iniciar total de juros com duas casas decimais
        BigDecimal totalJuros = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        for(int i = 1; i <= simulacao.prazoMeses; i++){

            // Juros do mês com arredondamento
            BigDecimal juros = saldo.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
            // Saldo final do mês com arredondamento
            BigDecimal saldoFinal = saldo.add(juros).setScale(2, RoundingMode.HALF_UP);

            MemoriaCalculo m= new MemoriaCalculo();

            m.mes= i;
            m.saldoInicial= saldo;
            m.juros= juros;
            m.saldoFinal= saldoFinal;
            m.simulacao= simulacao;

            memoria.add(m);

            // Atualiza o total de juros com arredondamento
            totalJuros = totalJuros.add(juros).setScale(2, RoundingMode.HALF_UP);
            // Próximo mês
            saldo= saldoFinal;
        }

        simulacao.memoria= memoria;
        simulacao.valorTotalFinal= saldo.setScale(2, RoundingMode.HALF_UP);
        simulacao.valorTotalJuros= totalJuros.setScale(2, RoundingMode.HALF_UP);

        repository.persist(simulacao);

        return simulacao;
    }

    public Simulacao buscarPorId(Long id){
        return repository.findById(id);
    }
}