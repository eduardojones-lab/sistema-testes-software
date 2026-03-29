package br.udesc.ceavi.sistemaTestes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação.
 *
 * @SpringBootApplication combina três anotações:
 *   - @Configuration: define que esta classe pode ter configurações Spring
 *   - @EnableAutoConfiguration: habilita a configuração automática do Spring Boot
 *   - @ComponentScan: varre o pacote em busca de componentes Spring (@Controller, @Service, etc.)
 *
 * Ponto de entrada: o método main() inicia o servidor embutido (Tomcat).
 */
@SpringBootApplication
public class SistemaTestesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaTestesApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Sistema de Testes - UDESC CEAVI/ Aula 11/06");
        System.out.println("  Servidor rodando em: http://localhost:8080");
        System.out.println("  Produzido pelo aluno: Eduardo Jones");
        System.out.println("==============================================");
    }
}
