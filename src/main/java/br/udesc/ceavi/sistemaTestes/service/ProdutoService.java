package br.udesc.ceavi.sistemaTestes.service;

import br.udesc.ceavi.sistemaTestes.exception.RecursoNaoEncontradoException;
import br.udesc.ceavi.sistemaTestes.model.Produto;
import br.udesc.ceavi.sistemaTestes.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * SERVIÇO: ProdutoService
 *
 * @Service = componente Spring que contém a LÓGICA DE NEGÓCIO.
 *
 * Arquitetura em camadas (padrão MVC estendido):
 *   Controller → Service → Repository → Banco de Dados
 *
 * Por que separar em Service?
 *   - Separação de responsabilidades (princípio SOLID)
 *   - A lógica de negócio fica isolada do Controller (que só recebe requisições HTTP)
 *   - Facilita a escrita de testes unitários
 *   - Se mudar o banco, só muda o Repository; o Service não é afetado
 *
 * @Autowired = injeção de dependência do Spring (IoC - Inversão de Controle)
 * Em vez de criar o Repository com "new", pedimos ao Spring que o forneça.
 */
@Service
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    /**
     * Injeção de dependência via construtor (melhor prática)
     */
    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // ========== OPERAÇÕES CRUD ==========

    /**
     * CRIAR: Salva um novo produto no banco de dados.
     * @Transactional garante que a operação é atômica (tudo ou nada)
     */
    public Produto criar(Produto produto) {
        return produtoRepository.save(produto);
    }

    /**
     * LER TODOS: Retorna todos os produtos cadastrados.
     * @Transactional(readOnly = true): otimização para operações de leitura
     */
    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    /**
     * LER POR ID: Busca um produto específico pelo ID.
     * Lança RecursoNaoEncontradoException se não encontrar → gera HTTP 404
     */
    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto", id));
    }

    /**
     * ATUALIZAR: Atualiza os dados de um produto existente.
     * Primeiro verifica se existe (lança 404 se não existir), depois atualiza.
     */
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        // Verifica se o produto existe (lança exceção se não existir)
        Produto produtoExistente = buscarPorId(id);

        // Atualiza os campos
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
        produtoExistente.setCategoria(produtoAtualizado.getCategoria());

        // Salva as alterações (JPA detecta que já existe e faz UPDATE)
        return produtoRepository.save(produtoExistente);
    }

    /**
     * DELETAR: Remove um produto pelo ID.
     */
    public void deletar(Long id) {
        // Verifica se existe antes de deletar
        buscarPorId(id);
        produtoRepository.deleteById(id);
    }

    // ========== BUSCAS ESPECIALIZADAS ==========

    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarComEstoqueBaixo(Integer limite) {
        return produtoRepository.findByQuantidadeEstoqueLessThan(limite);
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax);
    }

    /**
     * REGRA DE NEGÓCIO: Adiciona unidades ao estoque de um produto.
     * Demonstra como validações de negócio ficam no Service.
     */
    public Produto adicionarEstoque(Long id, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        Produto produto = buscarPorId(id);
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        return produtoRepository.save(produto);
    }

    /**
     * REGRA DE NEGÓCIO: Conta o total de produtos cadastrados.
     */
    @Transactional(readOnly = true)
    public long contarProdutos() {
        return produtoRepository.count();
    }
}
