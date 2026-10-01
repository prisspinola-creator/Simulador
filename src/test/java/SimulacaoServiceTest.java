import entity.Simulacao;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulacaoServiceTest {

    @Inject
    service.SimulacaoService service;

    @Test
    public void deveCalcularSimulacaoCorretamente() {
        Simulacao simulacao = new Simulacao();
        simulacao.valorInicial = new BigDecimal("1000");
        simulacao.taxaJurosMensal = new BigDecimal("1.5");
        simulacao.prazoMeses = 12;

        Simulacao resultado = service.calcularSimulacao(simulacao);

        assertNotNull(resultado);
        assertNotNull(resultado.valorTotalFinal);
        assertNotNull(resultado.valorTotalJuros);
        assertNotNull(resultado.memoria);

        assertEquals(12, resultado.memoria.size());

        assertEquals(0, resultado.valorTotalFinal.compareTo(new BigDecimal("1195.63")));
        assertEquals(0, resultado.valorTotalJuros.compareTo(new BigDecimal("195.63")));

        assertEquals(1, resultado.memoria.get(0).mes);
        assertEquals(0, resultado.memoria.get(0).saldoInicial.compareTo(new BigDecimal("1000.00")));
        assertEquals(0, resultado.memoria.get(0).juros.compareTo(new BigDecimal("15.00")));
        assertEquals(0, resultado.memoria.get(0).saldoFinal.compareTo(new BigDecimal("1015.00")));
    }

    @Test
    public void deveRetornarMemoriaVaziaEJurosZeroQuandoPrazoForZero() {
        Simulacao simulacao = new Simulacao();
        simulacao.valorInicial = new BigDecimal("1000");
        simulacao.taxaJurosMensal = new BigDecimal("1.5");
        simulacao.prazoMeses = 0;

        Simulacao resultado = service.calcularSimulacao(simulacao);

        assertNotNull(resultado);
        assertNotNull(resultado.memoria);
        assertTrue(resultado.memoria.isEmpty());

        assertEquals(0, resultado.valorTotalFinal.compareTo(new BigDecimal("1000.00")));
        assertEquals(0, resultado.valorTotalJuros.compareTo(new BigDecimal("0.00")));
    }
}