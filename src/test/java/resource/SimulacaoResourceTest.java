package resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SimulacaoResourceTest {

    @Test
    public void deveCriarSimulacao() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 1000,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
            """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void deveBuscarSimulacaoExistente() {
        Number idNum =
                given()
                        .contentType("application/json")
                        .body("""
                    {
                        "valorInicial": 1000,
                        "taxaJurosMensal": 1.5,
                        "prazoMeses": 12
                    }
                """)
                        .when()
                        .post("/simulacoes")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        Long id = idNum.longValue();

        given()
                .when()
                .get("/simulacoes/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void deveRetornar404QuandoNaoExiste() {
        given()
                .when()
                .get("/simulacoes/999999")
                .then()
                .statusCode(404);
    }

    @Test
    public void deveRetornar400ParaEntradaInvalidaPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 0,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
            """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaEntradaNegativaPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": -1000,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
            """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaPrazoInvalidoPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 1000,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 0
                }
            """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaTaxaInvalidaPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 1000,
                    "taxaJurosMensal": 0,
                    "prazoMeses": 12
                }
            """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaValorInicialNuloPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": null,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
                """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaTaxaJurosNulaPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 1000,
                    "taxaJurosMensal": null,
                    "prazoMeses": 12
                }
                """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaPrazoNuloPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 1000,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": null
                }
                """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaEntradaFaltandoCamposPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
                """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaEntradaVaziaPOST() {
        given()
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400)
                .body(is("Entradas inválidas"));
    }

    @Test
    public void deveRetornar400ParaIdInvalidoGET() {
        given()
                .when()
                .get("/simulacoes/abc")
                .then()
                .statusCode(400)
                .body(is("ID inválido"));
    }

    @Test
    public void deveRetornarMemoriaComTamanhoCorreto() {
        Number idNum =
                given()
                        .contentType("application/json")
                        .body("""
                    {
                        "valorInicial": 1000,
                        "taxaJurosMensal": 1.5,
                        "prazoMeses": 12
                    }
                """)
                        .when()
                        .post("/simulacoes")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        Long id = idNum.longValue();

        given()
                .when()
                .get("/simulacoes/" + id)
                .then()
                .statusCode(200)
                .body("memoria.size()", is(12));
    }

    // Testes adicionais de melhoria de cobertura no POST (valores faltando)
    @Test
    public void deveRetornar400ParaEntradaUSandoCamposFaltandoPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": 1000,
                    "prazoMeses": 12
                }
                """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400);
    }

    @Test
    public void deveRetornar400ParaEntradaComNuloEMissingPOST() {
        given()
                .contentType("application/json")
                .body("""
                {
                    "valorInicial": null,
                    "taxaJurosMensal": null
                }
                """)
                .when()
                .post("/simulacoes")
                .then()
                .statusCode(400);
    }
}