GUIA DE CONFIGURAÇÃO DO ZERO - Sistema de Testes

````
PASSO 1: Instalar as Ferramentas

1.1 Java JDK 17
Acesse: https://adoptium.net/
Baixe: Eclipse Temurin 17 (LTS)
Instale normalmente
Verifique: abra o terminal (CMD) e digite:
  java -version

  Deve aparecer: openjdk version "17..."


  1.2 IntelliJ IDEA
Acesse: https://www.jetbrains.com/idea/download/
Baixe a versão Community (gratuita)
Instale normalmente


  1.3 PostgreSQL + pgAdmin
Acesse: https://www.postgresql.org/download/
Baixe e instale o PostgreSQL (inclui o pgAdmin)
Durante a instalação:

  Porta: 5432 (padrão)
  Senha do superusuário (postgres): postgres!
pgAdmin é instalado junto automaticamente


  1.4 Postman
Acesse: https://www.postman.com/downloads/
Baixe e instale (versão gratuita)

````
````
PASSO 2: Criar o Banco de Dados no pgAdmin
1. Abra o pgAdmin 4
2. No painel esquerdo, expanda Servers → PostgreSQL
3. Digite a senha configurada na instalação
4. Clique com o botão direito em Databases → Create → Database...
5. Na janela que abrir:
  Database: sistema\_testes
  Owner: postgres
6. Clique em Save
O banco sistema\_testes foi criado.
````


````
PASSO 3: Abrir o Projeto no IntelliJ
1. Abra o IntelliJ IDEA
2. Clique em Open
3. Navegue até a pasta sistema-testes e clique OK
4. O IntelliJ vai detectar o pom.xml e perguntar se é um projeto Maven
5. Clique Trust Project (Confiar no Projeto)
6. Aguarde o IntelliJ baixar as dependências (barra de progresso no canto inferior)

 Isso pode levar 2-5 minutos na primeira vez!
````

````
PASSO 4: Verificar a Configuração do Banco
Abra o arquivo:
src/main/resources/application.properties

Verifique se as configurações batem com o que você instalou:
properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema\_testes
spring.datasource.username=postgres
spring.datasource.password=postgres!  ← altere se você usou outra senha
````

````
PASSO 5: Rodar a Aplicação

Opção: Pelo IntelliJ 

1. No painel esquerdo, navegue até:

src/main/java/br/udesc/ceavi/sistemaTestes/SistemaTestesApplication.java

2. Clique com o botão direito → Run 'SistemaTestesApplication'
OU clique no botão ▶️ verde ao lado do método "main"


Confirmação de sucesso ✅
No console do IntelliJ, você verá:

    ==============================================
Sistema de Testes - UDESC CEAVI
Servidor rodando em: http://localhost:8080
Produzido pelos alunos: Cauê, Eduardo, Henrique e Thiago.

    ==============================================
````



````
PASSO 6: Verificar as Tabelas no pgAdmin

1. No pgAdmin, expanda:
sistema\_testes → Schemas → public → Tables
2. Você deverá ver as tabelas:
    produtos
    usuarios
3. Clique com o botão direito em "produtos" → View/Edit Data → All Rows

A tabela estará vazia por enquanto (vamos adicionar dados com o Postman)
````

````
PASSO 7: Testar com o Postman

1. Abra o Postman
2. Faça sua primeira requisição de teste:

    Método: GET
URL: http://localhost:8080/api/produtos

3. Clique em Send
4. Você deve ver:
json
\[]

(lista vazia, ainda não cadastramos nada)

Se chegou até aqui, o projeto está funcionando! 🎉
````

````
PROBLEMAS COMUNS E SOLUÇÕES ⚠️

"Unable to connect to database"

Verifique se o PostgreSQL está rodando
Windows: Painel de Controle → Serviços → procure "postgresql" → Iniciar
Verifique a senha em `application.properties`

 "Port 8080 already in use"

Outra aplicação está usando a porta 8080
Solução: mude para 8081 em `application.properties`:
properties
server.port=8081


"Cannot resolve symbol" no IntelliJ

As dependências não baixaram ainda
Solução: File → Invalidate Caches → Invalidate and Restart

Maven não encontrado

No IntelliJ: Build → Build Project (Ctrl+F9)
Isso força o IntelliJ a recarregar o pom.xml
````

````
ESTRUTURA DO PROJETO (para entender o que foi criado) 📁


  sistema-testes/
│
├── pom.xml   → Dependências do projeto (como um requirements.txt)
│
└── src/main/java/br/udesc/ceavi/sistemaTestes/
│
├── SistemaTestesApplication.java    → Ponto de entrada (método main)
│
├── model/
│   ├── Produto.java                 → Entidade Produto (tabela no banco)
│   └── Usuario.java                 → Entidade Usuário (tabela no banco)
│
├── repository/
│   ├── ProdutoRepository.java       → Acesso ao banco para Produto
│   └── UsuarioRepository.java       → Acesso ao banco para Usuário
│
├── service/
│   ├── ProdutoService.java          → Regras de negócio dos produtos
│   └── UsuarioService.java          → Regras de negócio dos usuários
│
├── controller/
│   ├── ProdutoController.java       → Endpoints REST de produtos
│   └── UsuarioController.java       → Endpoints REST de usuários
│
└── exception/
├── RecursoNaoEncontradoException.java  → Exceção customizada (404)
└── GlobalExceptionHandler.java          → Tratamento central de erros
````

````
  FLUXO DE UMA REQUISIÇÃO 🗺️


  Postman
↓ envia HTTP POST /api/produtos com JSON no body
↓
ProdutoController.java
↓ recebe a requisição, valida com @Valid, chama o Service
↓
ProdutoService.java
↓ aplica regras de negócio, chama o Repository
↓
ProdutoRepository.java
↓ executa o SQL no banco (INSERT INTO produtos ...)
↓
PostgreSQL (banco de dados)
↓ salva os dados, retorna o objeto com ID gerado
↓
ProdutoService.java → ProdutoController.java
↓ retorna ResponseEntity com status 201 + JSON do produto
↓
Postman recebe a resposta
````


