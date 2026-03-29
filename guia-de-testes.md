📋 GUIA DE TESTES - POSTMAN
Sistema de Gerenciamento de Produtos | UDESC CEAVI - Aula Invertida



⚙️ CONFIGURAÇÃO INICIAL

1. Crie uma Collection no Postman
* Abra o Postman → clique em Collections → + (New Collection)
* Nomeie: Sistema de Testes - UDESC CEAVI
2. Configure a variável de ambiente
* Vá em Environments → + (New)
* Nome: Local
* Adicione a variável:

  * Variable: base\_url
  * Initial value: http://localhost:8080
* Selecione este environment antes de testar

\#3. URL base de todos os testes

{{base\_url}}







🟢 PARTE 1: TESTES FUNCIONAIS
Verificam se as funcionalidades do sistema funcionam corretamente



👤 CADASTRO DE USUÁRIOS

F-01 | Cadastrar usuário com sucesso

Método:  POST
URL:     {{base\_url}}/api/usuarios
Headers: Content-Type: application/json

Body (raw → JSON):
{
"nome": "João Silva",
"email": "joao.silva@udesc.br",
"senha": "senha123",
"perfil": "USER"
}

✅ Resultado esperado:
Status: 201 Created
Body: objeto do usuário criado com ID gerado





F-02 | Cadastrar usuário ADMIN

Método:  POST
URL:     {{base\_url}}/api/usuarios

Body:
{
"nome": "Maria Admin",
"email": "maria.admin@udesc.br",
"senha": "admin@123",
"perfil": "ADMIN"
}

✅ Resultado esperado: Status 201 Created





F-03 | Listar todos os usuários

Método:  GET
URL:     {{base\_url}}/api/usuarios

✅ Resultado esperado:
Status: 200 OK
Body: array com todos os usuários





F-04 | Buscar usuário por ID

Método:  GET
URL:     {{base\_url}}/api/usuarios/1

✅ Resultado esperado: Status 200 OK com dados do usuário





F-05 | Realizar login com sucesso

Método:  POST
URL:     {{base\_url}}/api/usuarios/login

Body:
{
"email": "joao.silva@udesc.br",
"senha": "senha123"
}

✅ Resultado esperado:
Status: 200 OK
Body: token + dados do usuário





📦 CADASTRO DE PRODUTOS

F-06 | Criar produto com sucesso

Método:  POST
URL:     {{base\_url}}/api/produtos

Body:
{
"nome": "Notebook Dell Inspiron",
"descricao": "Notebook 15 polegadas, i5, 8GB RAM, 256GB SSD",
"preco": 2499.90,
"quantidadeEstoque": 15,
"categoria": "Informatica"
}

✅ Resultado esperado: Status 201 Created





F-07 | Criar mais produtos (para ter dados nos testes)

Produto 2:
{
"nome": "Mouse Logitech MX Master",
"descricao": "Mouse sem fio ergonômico",
"preco": 349.90,
"quantidadeEstoque": 50,
"categoria": "Informatica"
}

Produto 3:
{
"nome": "Cadeira Gamer Pro",
"descricao": "Cadeira ergonômica para gamers",
"preco": 899.00,
"quantidadeEstoque": 8,
"categoria": "Moveis"
}

Produto 4:
{
"nome": "Teclado Mecânico",
"descricao": "Teclado mecânico com switch Red",
"preco": 299.99,
"quantidadeEstoque": 3,
"categoria": "Informatica"
}





F-08 | Listar todos os produtos

Método:  GET
URL:     {{base\_url}}/api/produtos

✅ Resultado esperado: Status 200 + lista de produtos





F-09 | Buscar produto por ID

Método:  GET
URL:     {{base\_url}}/api/produtos/1

✅ Resultado esperado: Status 200 + dados do produto





F-10 | Atualizar produto

Método:  PUT
URL:     {{base\_url}}/api/produtos/1

Body:
{
"nome": "Notebook Dell Inspiron ATUALIZADO",
"descricao": "Notebook 15 polegadas, i7, 16GB RAM, 512GB SSD",
"preco": 3299.90,
"quantidadeEstoque": 12,
"categoria": "Informatica"
}

✅ Resultado esperado:
Status: 200 OK
Body: produto com dados atualizados



F-11 | Buscar por categoria

Método:  GET
URL:     {{base\_url}}/api/produtos/categoria/Informatica

✅ Resultado esperado: lista de produtos da categoria





F-12 | Buscar por nome (contém texto)

Método:  GET
URL:     {{base\_url}}/api/produtos/buscar?nome=notebook

✅ Resultado esperado: produtos cujo nome contém "notebook"





F-13 | Adicionar estoque via PATCH

Método:  PATCH
URL:     {{base\_url}}/api/produtos/1/estoque?quantidade=5

✅ Resultado esperado:
Status: 200 OK
quantidadeEstoque aumentada em 5





F-14 | Deletar produto

Método:  DELETE
URL:     {{base\_url}}/api/produtos/4

✅ Resultado esperado: Status 204 No Content (sem corpo)







🔴 PARTE 2: TESTES DE SEGURANÇA
Verificam se o sistema protege contra dados inválidos e acesso indevido



S-01 | Tentar cadastrar usuário com email duplicado

Método:  POST
URL:     {{base\_url}}/api/usuarios

Body (mesmo email do F-01):
{
"nome": "Outro João",
"email": "joao.silva@udesc.br",
"senha": "outrasenha123",
"perfil": "USER"
}

❌ Resultado esperado:
Status: 409 Conflict
Body: { "erro": "Conflito de dados", "mensagem": "Já existe um usuário..." }





S-02 | Tentar login com senha errada

Método:  POST
URL:     {{base\_url}}/api/usuarios/login

Body:
{
"email": "joao.silva@udesc.br",
"senha": "senhaerrada999"
}

❌ Resultado esperado:
Status: 409 Conflict
Body: { "mensagem": "Senha incorreta" }





S-03 | Tentar login com email inexistente

Método:  POST
URL:     {{base\_url}}/api/usuarios/login

Body:
{
"email": "hacker@malicioso.com",
"senha": "tentativa123"
}

❌ Resultado esperado:
Status: 404 Not Found





S-04 | Criar produto com preço negativo (validação de campo)

Método:  POST
URL:     {{base\_url}}/api/produtos

Body:
{
"nome": "Produto Teste",
"descricao": "Teste",
"preco": -100.00,
"quantidadeEstoque": 10,
"categoria": "Teste"
}

❌ Resultado esperado:
Status: 400 Bad Request
Body: { "campos": { "preco": "O preço deve ser maior que zero" } }





S-05 | Criar usuário sem email (campo obrigatório)

Método:  POST
URL:     {{base\_url}}/api/usuarios

Body:
{
"nome": "Sem Email",
"senha": "senha123"
}

❌ Resultado esperado:
Status: 400 Bad Request
Body: { "campos": { "email": "O email é obrigatório" } }





S-06 | Email com formato inválido

Body:
{
"nome": "Teste",
"email": "isso-nao-e-um-email",
"senha": "senha123"
}

❌ Resultado esperado:
Status: 400 Bad Request
Body: { "campos": { "email": "Formato de email inválido" } }





S-07 | Senha com menos de 8 caracteres

Body:
{
"nome": "Teste",
"email": "teste@test.com",
"senha": "123"
}

❌ Resultado esperado:
Status: 400 Bad Request
Body: { "campos": { "senha": "A senha deve ter no mínimo 8 caracteres" } }





S-08 | Buscar ID inexistente (SQL Injection simulado)

Método:  GET
URL:     {{base\_url}}/api/produtos/99999

❌ Resultado esperado:
Status: 404 Not Found
Body: { "erro": "Recurso não encontrado" }





S-09 | Adicionar quantidade negativa ao estoque

Método:  PATCH
URL:     {{base\_url}}/api/produtos/1/estoque?quantidade=-5

❌ Resultado esperado:
Status: 409 Conflict
Body: { "mensagem": "A quantidade deve ser maior que zero" }







⚡ PARTE 3: TESTES DE DESEMPENHO
Verificam o tempo de resposta e comportamento sob carga



D-01 | Medir tempo de resposta - GET simples

Método:  GET
URL:     {{base\_url}}/api/produtos

📊 Observar no Postman:
→ No canto inferior direito: "200 OK  XX ms"
✅ Meta: resposta em menos de 500ms





D-02 | Medir tempo de resposta - GET com filtro

Método:  GET
URL:     {{base\_url}}/api/produtos/categoria/Informatica

📊 Comparar com D-01:
✅ Meta: resposta em menos de 500ms
📝 Observar: filtro no banco costuma ser mais lento que lista simples?





D-03 | Medir tempo de resposta - POST (escrita no banco)

Método:  POST
URL:     {{base\_url}}/api/produtos

Body:
{
"nome": "Produto Performance Test",
"preco": 99.99,
"quantidadeEstoque": 100,
"categoria": "Teste"
}

📊 Comparar com D-01:
📝 Observar: operações de escrita são mais lentas que leitura?





D-04 | Teste de Estatísticas (endpoint complexo)

Método:  GET
URL:     {{base\_url}}/api/produtos/estatisticas

📊 Este endpoint faz múltiplas consultas ao banco.
✅ Meta: ainda abaixo de 1000ms
📝 Comparar com D-01 - a diferença mostra o custo de múltiplas queries





D-05 | Teste de Carga com Runner do Postman

1. Selecione a request "F-08 Listar todos os produtos"
2. Na lateral esquerda, clique com o botão direito sobre My Collection.
2.1. Clique em "Run".
3. Configure:

   * Iterations: 50 (50 repetições)
   * Delay: 0ms
4. Observe os resultados:

   * Tempo médio por requisição
   * Se houve alguma falha
   * Variação de tempo entre requests

📊 Métricas a analisar:

* Tempo mínimo
* Tempo máximo
* Tempo médio
✅ Meta: nenhuma falha, tempo médio < 500ms





D-06 | Comparativo: GET vs POST vs DELETE

Execute em sequência e anote os tempos:

1. GET  /api/produtos          → \_\_\_\_ ms
2. POST /api/produtos          → \_\_\_\_ ms
3. GET  /api/produtos/1        → \_\_\_\_ ms
4. PUT  /api/produtos/1        → \_\_\_\_ ms
5. DELETE /api/produtos/X      → \_\_\_\_ ms

📊 Conclusão esperada:
Leitura (GET) geralmente é mais rápida que escrita (POST/PUT/DELETE)







🎨 PARTE 4: TESTES DE USABILIDADE DA API
Verificam se a API comunica erros de forma clara e retorna dados bem estruturados\*=



U-01 | Verificar estrutura da resposta de sucesso

Método:  GET
URL:     {{base\_url}}/api/produtos/1

✅ Checklist de usabilidade:
□ Status code correto? (200)
□ Content-Type: application/json no header?
□ Todos os campos esperados presentes? (id, nome, preco, etc.)
□ Datas em formato legível (ISO 8601)?
□ Campos numéricos com tipo correto (número, não string)?





U-02 | Verificar estrutura da resposta de erro

Método:  GET
URL:     {{base\_url}}/api/produtos/99999 (ID inexistente)

✅ Checklist de usabilidade:
□ Status code correto? (404)
□ Mensagem de erro em português?
□ Campo "timestamp" presente?
□ Mensagem é clara o suficiente para o usuário entender?
□ NÃO expõe detalhes internos do servidor (stack trace)?





U-03 | Verificar resposta de criação

Método:  POST
URL:     {{base\_url}}/api/produtos

Body:
{
"nome": "Produto Usabilidade",
"preco": 150.00,
"quantidadeEstoque": 20,
"categoria": "Teste"
}

✅ Checklist:
□ Status 201 (não apenas 200)?
□ Retorna o objeto criado com ID?
□ dataCriacao foi preenchida automaticamente?
□ dataAtualizacao igual à dataCriacao na criação?





U-04 | Verificar consistência dos erros de validação

Método:  POST
URL:     {{base\_url}}/api/usuarios

Body:
{
"nome": "X",
"email": "emailinvalido",
"senha": "123"
}

✅ Checklist:
□ Retorna TODOS os erros de uma vez (não só o primeiro)?
□ Mensagens são específicas por campo?
□ Body tem estrutura: { "campos": { "nomeCampo": "mensagem" } }?
□ Usuário sabe o que corrigir sem fazer nova tentativa?





U-05 | Verificar resposta do DELETE

Método:  DELETE
URL:     {{base\_url}}/api/produtos/3

✅ Checklist:
□ Status 204 (não 200)?
□ Body VAZIO (não retorna nada)?
□ Tentando deletar o mesmo ID novamente → retorna 404?





U-06 | Verificar Header Content-Type

Em qualquer resposta bem-sucedida, vá em Headers e verifique:

✅ Checklist:
□ Content-Type: application/json;charset=UTF-8
□ Headers não expõem informações sensíveis do servidor?







📊 TABELA DE RESUMO DOS TESTES

|ID|Tipo|Endpoint|Esperado|
|-|-|-|-|-|
|F-01|Funcional|POST /api/usuarios|201|
|F-05|Funcional|POST /api/usuarios/login|200|
|F-06|Funcional|POST /api/produtos|201|
|F-08|Funcional|GET  /api/produtos|200|
|F-10|Funcional|PUT  /api/produtos/{id}|200|
|F-14|Funcional|DELETE /api/produtos/{id}|204|
|S-01|Segurança|POST /api/usuarios (duplicado)|409|
|S-02|Segurança|POST /login (senha errada)|409|
|S-04|Segurança|POST produto preço negativo|400|
|S-06|Segurança|POST email inválido|400|
|D-01|Desempenho|GET /api/produtos|<500ms|
|D-05|Desempenho|Runner 50x|sem falha|
|U-01|Usabilidade|GET /api/produtos/{id}|JSON ok|
|U-04|Usabilidade|POST múltiplos erros de uma vez|todos|





