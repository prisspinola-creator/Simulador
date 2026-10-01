Desafio Técnico - Simulador de Financiamentos

Descrição
API backend para simulação de financiamentos com cálculo de juros compostos, geração de memória de cálculo mês a mês e persistência em banco H2.

Tecnologias utilizadas

Java 25
Quarkus
Maven Wrapper
H2 Database
Hibernate ORM Panache
OpenAPI / Swagger
JUnit 5
Rest Assured
JaCoCo

Pré-requisitos

JDK 25 instalado e configurado no ambiente
Terminal:
Windows: PowerShell ou CMD
macOS / Linux: Terminal

Estrutura do projeto

src/main/java/resource → endpoints da API
src/main/java/service → regras de negócio
src/main/java/repository → persistência
src/main/java/entity → entidades
src/test/java → testes automatizados

Comando exato para compilar, rodar a suíte de testes e validar a cobertura mínima de 80%

Windows
.\mvnw.cmd clean verify
macOS / Linux
chmod +x mvnw
./mvnw clean verify

O que esse comando faz:
compila o projeto
executa a suíte de testes
gera o relatório do JaCoCo
valida a cobertura mínima exigida de 80%

Como rodar apenas os testes

Windows
.\mvnw.cmd test
macOS / Linux
chmod +x mvnw
./mvnw test

Como validar a cobertura

Após executar o comando clean verify, abrir o relatório gerado em:
target/site/jacoco/index.html

Como executar a aplicação

Windows
.\mvnw.cmd quarkus:dev
macOS / Linux
chmod +x mvnw
./mvnw quarkus:dev

A aplicação ficará disponível em:
http://localhost:8081
Swagger / OpenAPI
Swagger UI
http://localhost:8081/q/swagger-ui
OpenAPI
http://localhost:8081/q/openapi
Endpoints principais
Criar simulação
POST /simulacoes

Exemplo de payload: { "valorInicial": 1000, "taxaJurosMensal": 1.5, "prazoMeses": 12 }

Buscar simulação por ID
GET /simulacoes/{id}

Exemplo: GET /simulacoes/1

Regras implementadas

cálculo de juros compostos mês a mês
geração da memória de cálculo
persistência da simulação e da memória
consulta por ID
validação de entrada
retornos HTTP:
201 criação com sucesso
200 consulta com sucesso
400 dados inválidos
404 simulação não encontrada

Precisão financeira

Os valores monetários foram implementados com BigDecimal, utilizando arredondamento com RoundingMode.HALF_UP.

Cobertura de testes
O projeto possui testes automatizados com JaCoCo e validação de cobertura mínima de 80% no comando verify.

Banco de dados
A aplicação utiliza H2 Database em modo embarcado, sem necessidade de Docker.