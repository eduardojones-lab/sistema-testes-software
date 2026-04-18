## GUIA DE TESTES - POSTMAN
Sistema de Gerenciamento de Produtos 



### CONFIGURAÇÃO INICIAL
````
Crie uma Collection no Postman
1. Abra o Postman → clique em Collections → + (New Collection)
Nomeie: Sistema de Testes 
2. Configure a variável de ambiente
Vá em Environments → + (New)
Nome: Local
Adicione a variável:
Variable: base_url
Value: http://localhost:8080
Selecione este Environment antes de testar

3. URL base de todos os testes
http://localhost:8080
````






### PARTE 1: TESTES FUNCIONAIS 🟢
Verificam se as funcionalidades do sistema funcionam corretamente



#### CADASTRO DE USUÁRIOS
````
CADASTRO DE PRODUTOS📦 
F-01 | Criar produto com sucesso
Método:  POST
URL:     http://localhost:8080/api/produtos
Body:
{
"nome": "Notebook Dell Inspiron",
"descricao": "Notebook 15 polegadas, i5, 8GB RAM, 256GB SSD",
"preco": 2499.90,
"quantidadeEstoque": 15,
"categoria": "Informatica"
}
Resultado esperado: Status 201 Created ✅ 


F-02 | Criar mais produtos (para ter dados nos testes)
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


F-03 | Listar todos os produtos
Método:  GET
URL:     http://localhost:8080/api/produtos
Resultado esperado: Status 200 + lista de produtos ✅ 


F-04 | Buscar produto por ID
Método:  GET
URL:     http://localhost:8080/api/produtos/1
Resultado esperado: Status 200 + dados do produto ✅ 


F-05 | Atualizar produto
Método:  PUT
URL:     http://localhost:8080/api/produtos/1
Body:
{
"nome": "Notebook Dell Inspiron ATUALIZADO",
"descricao": "Notebook 15 polegadas, i7, 16GB RAM, 512GB SSD",
"preco": 3299.90,
"quantidadeEstoque": 12,
"categoria": "Informatica"
}
Resultado esperado ✅:
Status: 200 OK
Body: produto com dados atualizados


F-06 | Deletar produto
Método:  DELETE
URL:     http://localhost:8080/api/produtos/4
Resultado esperado: Status 204 No Content (sem corpo)  ✅
````






### PARTE 2: TESTES DE SEGURANÇA 🔴
Verificam se o sistema protege contra dados inválidos e acesso indevido


````
S-01 | Criar produto com preço negativo (validação de campo)
Método:  POST
URL:     http://localhost:8080/api/produtos
Body:
{
"nome": "Produto Teste",
"descricao": "Teste",
"preco": -100.00,
"quantidadeEstoque": 10,
"categoria": "Teste"
}
Resultado esperado ❌:
Status: 400 Bad Request
Body: { "campos": { "preco": "O preço deve ser maior que zero" } }


S-02 | Buscar ID inexistente
Método:  GET
URL:     http://localhost:8080/api/produtos/99999
Resultado esperado ❌:
Status: 404 Not Found
Body: { "erro": "Recurso não encontrado" }


S-03 | Adicionar quantidade negativa ao estoque
Método:  PATCH
URL:     http://localhost:8080/api/produtos/1/estoque?quantidade=-5
Resultado esperado ❌:
Status: 409 Conflict
Body: { "mensagem": "A quantidade deve ser maior que zero" }

````






### PARTE 3: TESTES DE DESEMPENHO ⚡ 
Verificam o tempo de resposta e comportamento sob carga


````
D-01 | Medir tempo de resposta - GET (pesquisa no banco)
Método:  GET
URL:     http://localhost:8080/api/produtos
Observar no Postman 📊:
→ No canto inferior direito: "200 OK  XX ms"
Meta: resposta em menos de 500ms ✅ 


D-02 | Medir tempo de resposta - GET com filtro
Método:  GET
URL:     http://localhost:8080/api/produtos/categoria/Informatica
Comparar com D-01 📊:
Meta: resposta em menos de 500ms  ✅


D-03 | Medir tempo de resposta - POST (escrita no banco)
Método:  POST
URL:     http://localhost:8080/api/produtos
Body:
{
"nome": "Produto Performance Test",
"preco": 99.99,
"quantidadeEstoque": 100,
"categoria": "Teste"
}
Comparar com D-01 📊:
Observar: Operações de escrita são mais lentas que as de leitura?



D-04 | Teste de Carga com Runner do Postman
Selecione a request "F-03 Listar todos os produtos"
Na lateral esquerda, clique com o botão direito sobre My Collection.
2.1. Clique em "Run", após em "Functional".
Configure:
Iterations: 50 (50 repetições)
Delay: 0ms
Métricas a analisar 📊:
- Tempo médio por requisição
- Se houve alguma falha
- Variação de tempo entre requests
Meta: nenhuma falha, Avg. Resp. Time < 500ms ✅ 


D-05 | Teste de Carga com Runner do Postman
Selecione a request "F-03 Listar todos os produtos"
Na lateral esquerda, clique com o botão direito sobre My Collection.
2.1. Clique em "Run", após em "Performance".
Configure:
1. Load profile: Fixed (20 usuários virtuais são executados por 2 minutos, cada um executando todas as solicitações sequencialmente.)
2. Load profile: Spike (4 usuários virtuais são executados por 40 segundos, aumentam para 20 em 20 segundos, caem para 4 em 30 segundos, mantêm 4 por 30 segundos, cada um executando todas as solicitações sequencialmente.)
Delay: 0ms
Métricas a analisar 📊:
- Total requests sent: Número total de solicitações enviadas aos endpoints.
- Requests/second:Número total de solicitações enviadas aos endpoints por segundo.
- Avg. response time:Média de tempo de resposta em todas as solicitações que chegam aos endpoints.
- P90:90% de solicitações concluídas neste período. Hepls identifica o desempenho típico para a maioria dos usuários.
- P95: 95% das solicitações concluídas dentro deste prazo. Indica a latência do limite superior para a maioria dos usuários.
- P99: 99% das solicitações foram concluídas dentro deste prazo. Destaca casos extremamente lentos que afetam apenas alguns usuários.
Meta: Nenhuma falha, comparar as métricas de cada teste. ✅

````





### PARTE 4: TESTES DE USABILIDADE DA API
Verificam se a API comunica erros de forma clara e retorna dados bem estruturados\*=


````
U-01 | Verificar estrutura da resposta de sucesso
Método:  GET
URL:     http://localhost:8080/api/produtos/1
Checklist de usabilidade ✅:
□ Status code correto? (200)
□ Content-Type: application/json no header?
□ Todos os campos esperados presentes? (id, nome, preço, descrição, preço, quantidade estoque, categoria, data criação e data atualização)
□ Datas em formato legível (ISO 8601)?
□ Campos numéricos com tipo correto (números)?


U-02 | Verificar estrutura da resposta de erro
Método:  GET
URL:     http://localhost:8080/api/produtos/99999 (ID inexistente)
Checklist de usabilidade  ✅:
□ Status code correto? (404)
□ Mensagem de erro em português?
□ Campo "timestamp" presente?
□ Mensagem é clara o suficiente para o usuário entender?
□ Não expõe detalhes internos do servidor (stack trace)?


U-03 | Verificar resposta de criação
Método:  POST
URL:     http://localhost:8080/api/produtos
Body:
{
"nome": "Produto Usabilidade",
"preco": 150.00,
"quantidadeEstoque": 20,
"categoria": "Teste"
}
Checklist ✅:
□ Status 201 (não apenas 200)?
□ Retorna o objeto criado com ID?
□ dataCriacao foi preenchida automaticamente?
□ dataAtualizacao igual à dataCriacao na criação?


U-04 | Verificar consistência dos erros de validação
Método:  POST
URL:     http://localhost:8080/api/usuarios
Body:
{
"nome": "X",
"email": "emailinvalido",
"senha": "123"
}
Checklist ✅:
□ Retorna todos os erros de uma vez (não só o primeiro)?
□ Mensagens são específicas por campo?
□ Body tem estrutura: { "campos": { "nomeCampo": "mensagem" } }?
□ Usuário sabe o que corrigir sem fazer nova tentativa?


U-05 | Verificar resposta do DELETE
Método:  DELETE
URL:     http://localhost:8080/api/produtos/3
Checklist ✅:
□ Status 204 (não 200)?
□ Body vazio (não retorna nada)?
□ Tentando deletar o mesmo ID novamente → retorna 404?

````






TABELA DE RESUMO DOS TESTES

|ID|Tipo|Endpoint|Esperado|Status|
|-|-|-|-|-|
|F-01|Funcional|POST /api/usuarios|201|✅|
|F-05|Funcional|POST /api/usuarios/login|200|✅|
|F-06|Funcional|POST /api/produtos|201|✅|
|F-08|Funcional|GET  /api/produtos|200|✅|
|F-10|Funcional|PUT  /api/produtos/{id}|200|✅|
|F-14|Funcional|DELETE /api/produtos/{id}|204|✅|
|S-01|Segurança|POST /api/usuarios (duplicado)|409|✅|
|S-02|Segurança|POST /login (senha errada)|409|✅|
|S-04|Segurança|POST produto preço negativo|400|✅|
|S-06|Segurança|POST email inválido|400|✅|
|D-01|Desempenho|GET /api/produtos|<500ms|✅|
|D-05|Desempenho|Runner 50x|sem falha|✅|
|U-01|Usabilidade|GET /api/produtos/{id}|JSON ok|✅|
|U-04|Usabilidade|POST múltiplos erros de uma vez|todos|✅|



