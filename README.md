# 🧪 Sistema de Testes de Software — UDESC CEAVI

> Projeto acadêmico desenvolvido para a **Aula Invertida** da disciplina de Engenharia de Software (3º semestre — UDESC CEAVI), demonstrando na prática a aplicação de um **Plano de Testes completo** sobre uma API REST Java com persistência em banco de dados.

---

## 📋 Sobre o Projeto

Este repositório contém uma **API REST completa** desenvolvida com Java 17 e Spring Boot, utilizada como **estudo de caso prático** para demonstrar os quatro tipos de testes de software:

| Tipo | O que verifica | Ferramenta |
|---|---|---|
| ✅ **Funcional** | Se o sistema faz o que foi especificado | Postman |
| 🔒 **Segurança** | Se o sistema rejeita entradas inválidas | Postman |
| ⚡ **Desempenho** | Tempo de resposta e comportamento sob carga | Postman Runner |
| 🎨 **Usabilidade** | Se a API comunica erros de forma clara | Postman |

O projeto foi planejado com base na norma **IEEE 829** (Standard for Software Test Documentation), aplicada em um modelo híbrido compacto — cobrindo as 10 seções essenciais do plano de testes.

---

## 🚀 Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

- **Java 17** — Linguagem principal (OOP)
- **Spring Boot 3.2** — Framework REST com servidor embutido
- **Spring Data JPA + Hibernate** — Persistência e mapeamento objeto-relacional
- **Bean Validation** — Validações declarativas nos modelos
- **PostgreSQL 15** — Banco de dados relacional
- **Postman** — Execução e automação dos testes

---

## 🏗️ Arquitetura

```
src/
└── main/
    └── java/br/udesc/ceavi/sistemaTestes/
        ├── SistemaTestesApplication.java   ← Ponto de entrada
        ├── controller/
        │   ├── ProdutoController.java      ← Endpoints REST de Produtos
        │   └── UsuarioController.java      ← Endpoints REST de Usuários
        ├── service/
        │   ├── ProdutoService.java         ← Regras de negócio
        │   └── UsuarioService.java         ← Regras de negócio
        ├── repository/
        │   ├── ProdutoRepository.java      ← Acesso ao banco
        │   └── UsuarioRepository.java      ← Acesso ao banco
        ├── model/
        │   ├── Produto.java                ← Entidade JPA
        │   └── Usuario.java                ← Entidade JPA
        └── exception/
            ├── RecursoNaoEncontradoException.java
            └── GlobalExceptionHandler.java ← Tratamento centralizado de erros
```

**Fluxo de uma requisição:**
```
Postman → Controller → Service → Repository → PostgreSQL
                                              ↓
Postman ← Controller ← Service ← Repository ←
```

---

## ⚙️ Como Executar

### Pré-requisitos

- [Java JDK 17](https://adoptium.net/) instalado
- [PostgreSQL 15](https://www.postgresql.org/download/) instalado e rodando
- [Postman](https://www.postman.com/downloads/) para executar os testes
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (recomendado)

### 1. Clone o repositório

```bash
git clone https://github.com/SEU_USUARIO/sistema-testes-software.git
cd sistema-testes-software
```

### 2. Configure o banco de dados

Abra o **pgAdmin** e crie um banco de dados chamado `sistema_testes`:

```sql
CREATE DATABASE sistema_testes;
```

### 3. Configure as credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_testes
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

### 4. Execute a aplicação

**Via IntelliJ:** Abra o projeto → clique com botão direito em `SistemaTestesApplication.java` → **Run**

**Via terminal:**
```bash
./mvnw spring-boot:run
```

### 5. Confirme que está rodando

```
==============================================
  Sistema de Testes - UDESC CEAVI
  Servidor rodando em: http://localhost:8080
==============================================
```

---

## 🌐 Endpoints Disponíveis

### 📦 Produtos — `/api/produtos`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `GET` | `/api/produtos` | Listar todos | 200 |
| `GET` | `/api/produtos/{id}` | Buscar por ID | 200 / 404 |
| `POST` | `/api/produtos` | Criar produto | 201 |
| `PUT` | `/api/produtos/{id}` | Atualizar produto | 200 |
| `DELETE` | `/api/produtos/{id}` | Deletar produto | 204 |
| `GET` | `/api/produtos/categoria/{cat}` | Filtrar por categoria | 200 |
| `GET` | `/api/produtos/buscar?nome=X` | Buscar por nome | 200 |
| `GET` | `/api/produtos/estoque-baixo?limite=5` | Estoque abaixo do limite | 200 |
| `GET` | `/api/produtos/faixa-preco?min=X&max=Y` | Por faixa de preço | 200 |
| `PATCH` | `/api/produtos/{id}/estoque?quantidade=5` | Adicionar ao estoque | 200 |
| `GET` | `/api/produtos/estatisticas` | Estatísticas gerais | 200 |

### 👤 Usuários — `/api/usuarios`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `GET` | `/api/usuarios` | Listar todos | 200 |
| `GET` | `/api/usuarios/{id}` | Buscar por ID | 200 / 404 |
| `POST` | `/api/usuarios` | Cadastrar usuário | 201 |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário | 200 |
| `DELETE` | `/api/usuarios/{id}` | Deletar usuário | 204 |
| `POST` | `/api/usuarios/login` | Login | 200 |
| `GET` | `/api/usuarios/count` | Contar usuários | 200 |

---

## 🧪 Plano de Testes — IEEE 829 (Modelo Híbrido)

O projeto inclui um **Plano de Testes completo** baseado na norma IEEE 829, aplicado de forma compacta com as 10 seções essenciais:

| # | Seção | Conteúdo |
|---|---|---|
| 01 | Identificador | PT-CEAVI-2025-01 |
| 02 | Referências | RF-001, HU-001~024 |
| 03 | Introdução | API REST de Produtos · Java 17 |
| 04 | Itens de Teste | Módulos Produtos e Usuários · 18 endpoints |
| 05 | Riscos | Falha no banco, dados inválidos, carga |
| 06 | Funcionalidades a Testar | CRUD completo + Login + Validações |
| 07 | Fora do Escopo | JWT, Interface gráfica, SQL Injection avançado |
| 08 | Abordagem | Caixa-preta via Postman · 4 tipos de teste |
| 09 | Critérios de Aprovação | 100% funcionais · < 500ms · Zero erros 5xx |
| 10 | Interrupção e Retomada | Banco indisponível · Mais de 30% falhas |

### Resumo dos Casos de Teste

```
Total de testes: 26
├── Funcionais:   8  (F-01 a F-08)
├── Segurança:    8  (S-01 a S-08)
├── Desempenho:   6  (D-01 a D-06)
└── Usabilidade:  5  (U-01 a U-05)
```

---

## 📝 Exemplos de Requisições

### Criar um produto (POST)

```json
POST http://localhost:8080/api/produtos
Content-Type: application/json

{
  "nome": "Notebook Dell Inspiron",
  "descricao": "Notebook 15 polegadas, i5, 8GB RAM, 256GB SSD",
  "preco": 2499.90,
  "quantidadeEstoque": 15,
  "categoria": "Informatica"
}
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "nome": "Notebook Dell Inspiron",
  "preco": 2499.90,
  "quantidadeEstoque": 15,
  "categoria": "Informatica",
  "dataCriacao": "2025-03-14T20:00:00"
}
```

### Login (POST)

```json
POST http://localhost:8080/api/usuarios/login
Content-Type: application/json

{
  "email": "joao@udesc.br",
  "senha": "senha123"
}
```

### Exemplo de erro de validação (400 Bad Request)

```json
{
  "timestamp": "2025-03-14T20:00:00",
  "status": 400,
  "erro": "Erro de validação",
  "campos": {
    "preco": "O preço deve ser maior que zero",
    "nome": "O nome do produto é obrigatório"
  }
}
```

---

## 🎓 Contexto Acadêmico

Este projeto foi desenvolvido como **Aula Invertida** para a disciplina de Engenharia de Software:

- **Instituição:** UDESC — Centro de Educação Superior do Alto Vale do Itajaí (CEAVI)
- **Curso:** Engenharia de Software
- **Semestre:** 3º Semestre
- **Formato:** Aula Invertida — apresentação preparada e ministrada pelos alunos
- **Tópicos abordados:**
  - Conceito de Plano de Testes
  - Norma IEEE 829 e seus modelos
  - Tipos de Testes: Funcional, Segurança, Desempenho e Usabilidade
  - Estudo de Caso prático com Java + PostgreSQL + Postman

---

## 📚 Conceitos de OO Aplicados

O projeto demonstra na prática os quatro pilares da Orientação a Objetos:

- **Encapsulamento** — atributos privados com getters/setters nas entidades
- **Abstração** — `JpaRepository` abstrai toda a camada SQL
- **Herança** — `RecursoNaoEncontradoException extends RuntimeException`
- **Polimorfismo** — `GlobalExceptionHandler` com múltiplos `@ExceptionHandler`

---

## 👨‍💻 Autor

Desenvolvido com 💙 por alunos da **UDESC CEAVI — Engenharia de Software**

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
