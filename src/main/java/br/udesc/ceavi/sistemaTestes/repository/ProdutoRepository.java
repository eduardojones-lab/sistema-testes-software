package br.udesc.ceavi.sistemaTestes.repository;

import br.udesc.ceavi.sistemaTestes.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * REPOSITÓRIO: ProdutoRepository
 *
 * Interface responsável pelo acesso ao banco de dados para a entidade Produto.
 *
 * Ao estender JpaRepository<Produto, Long>, ganhamos GRATUITAMENTE:
 *   - save(produto)         → INSERT ou UPDATE no banco
 *   - findById(id)          → SELECT por ID
 *   - findAll()             → SELECT * FROM produtos
 *   - deleteById(id)        → DELETE por ID
 *   - count()               → SELECT COUNT(*)
 *   - existsById(id)        → verifica se existe
 *   ... e muito mais!
 *
 * O Spring Data JPA gera a implementação automaticamente em runtime.
 * Nós só precisamos declarar a interface!
 *
 * Parâmetros do JpaRepository:
 *   - Produto: a entidade que este repositório gerencia
 *   - Long: o tipo da chave primária (id)
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Spring Data JPA "lê" o nome do método e gera a query automaticamente.
     * findByCategoria → SELECT * FROM produtos WHERE categoria = ?
     */
    List<Produto> findByCategoria(String categoria);

    /**
     * Busca produtos cujo nome contenha o texto informado (sem diferenciar maiúsculas)
     * findByNomeContainingIgnoreCase → SELECT * FROM produtos WHERE LOWER(nome) LIKE %texto%
     */
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca produtos com estoque abaixo de um limite
     * Useful para testes de regras de negócio
     */
    List<Produto> findByQuantidadeEstoqueLessThan(Integer quantidade);

    /**
     * Busca produtos com preço entre dois valores
     */
    List<Produto> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    /**
     * @Query: quando precisamos de uma query mais complexa, escrevemos JPQL manualmente
     * JPQL usa nomes das CLASSES Java, não das tabelas do banco
     */
    @Query("SELECT p FROM Produto p WHERE p.preco <= :precoMax AND p.quantidadeEstoque > 0 ORDER BY p.preco ASC")
    List<Produto> findProdutosDisponivelAtePreco(@Param("precoMax") BigDecimal precoMax);
}
