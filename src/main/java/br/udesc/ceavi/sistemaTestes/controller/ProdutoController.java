package br.udesc.ceavi.sistemaTestes.controller;

import br.udesc.ceavi.sistemaTestes.model.Produto;
import br.udesc.ceavi.sistemaTestes.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CONTROLADOR: ProdutoController
 *
 * @RestController = combina @Controller + @ResponseBody
 *   Indica que esta classe é um Controller REST (retorna JSON automaticamente)
 *
 * @RequestMapping("/api/produtos") = define a URL base para todos os endpoints desta classe
 *
 * Verbos HTTP utilizados (REST):
 *   GET    → buscar/listar dados
 *   POST   → criar novo recurso
 *   PUT    → atualizar recurso existente (todos os campos)
 *   DELETE → remover recurso
 *
 * Códigos HTTP de resposta:
 *   200 OK         → sucesso em GET, PUT
 *   201 Created    → sucesso em POST (criação)
 *   204 No Content → sucesso em DELETE (sem corpo na resposta)
 *   400 Bad Request → dados inválidos
 *   404 Not Found  → recurso não encontrado
 *   409 Conflict   → conflito de dados
 *   500 Internal Server Error → erro inesperado
 */
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (útil para testes)
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // ================================================
    // GET /api/produtos
    // Lista todos os produtos
    // ================================================
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos); // HTTP 200 + lista de produtos
    }

    // ================================================
    // GET /api/produtos/{id}
    // Busca um produto pelo ID
    // @PathVariable extrai o {id} da URL
    // ================================================
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // ================================================
    // POST /api/produtos
    // Cria um novo produto
    // @RequestBody converte o JSON enviado para objeto Java
    // @Valid ativa as validações definidas no model (@NotBlank, etc.)
    // ================================================
    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        Produto novoProduto = produtoService.criar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto); // HTTP 201
    }

    // ================================================
    // PUT /api/produtos/{id}
    // Atualiza um produto existente
    // ================================================
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // ================================================
    // DELETE /api/produtos/{id}
    // Remove um produto
    // ================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build(); // HTTP 204 (sem corpo)
    }

    // ================================================
    // GET /api/produtos/categoria/{categoria}
    // Filtra produtos por categoria
    // ================================================
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    // ================================================
    // GET /api/produtos/buscar?nome=xyz
    // Busca produtos pelo nome (query parameter)
    // @RequestParam extrai parâmetros da URL (?nome=valor)
    // ================================================
    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    // ================================================
    // GET /api/produtos/estoque-baixo?limite=10
    // Busca produtos com estoque abaixo do limite informado
    // ================================================
    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarEstoqueBaixo(
            @RequestParam(defaultValue = "10") Integer limite) {
        List<Produto> produtos = produtoService.buscarComEstoqueBaixo(limite);
        return ResponseEntity.ok(produtos);
    }

    // ================================================
    // GET /api/produtos/faixa-preco?min=10.00&max=100.00
    // Busca produtos dentro de uma faixa de preço
    // ================================================
    @GetMapping("/faixa-preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Produto> produtos = produtoService.buscarPorFaixaPreco(min, max);
        return ResponseEntity.ok(produtos);
    }

    // ================================================
    // PATCH /api/produtos/{id}/estoque?quantidade=5
    // Adiciona unidades ao estoque (atualização parcial)
    // @PatchMapping = atualização parcial de um recurso
    // ================================================
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Produto> adicionarEstoque(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {
        Produto produto = produtoService.adicionarEstoque(id, quantidade);
        return ResponseEntity.ok(produto);
    }

    // ================================================
    // GET /api/produtos/estatisticas
    // Retorna estatísticas do sistema (para testes de desempenho)
    // ================================================
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProdutos", produtoService.contarProdutos());
        stats.put("produtosComEstoqueBaixo", produtoService.buscarComEstoqueBaixo(5).size());
        stats.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(stats);
    }
}
