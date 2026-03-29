package br.udesc.ceavi.sistemaTestes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ENTIDADE: Produto
 *
 * Representa um produto no sistema.
 * Em OO, esta classe é o "molde" (blueprint) para criar objetos do tipo Produto.
 *
 * Anotações JPA utilizadas:
 *   @Entity    = diz ao JPA que esta classe representa uma tabela no banco
 *   @Table     = define o nome da tabela no banco
 *   @Id        = define a chave primária
 *   @Column    = configura detalhes da coluna no banco
 */
@Entity
@Table(name = "produtos")
public class Produto {

    // ========== ATRIBUTOS (campos da tabela no banco) ==========

    /**
     * @Id = chave primária
     * @GeneratedValue = valor gerado automaticamente pelo banco (auto increment)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @NotBlank = campo não pode ser vazio ou nulo
     * @Size = define tamanho mínimo e máximo
     * nullable = false: campo obrigatório no banco
     */
    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    /**
     * @Size = limita o tamanho da descrição
     */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    /**
     * @NotNull = não pode ser nulo
     * @DecimalMin = valor mínimo permitido (não pode ter preço negativo)
     * precision e scale = define casas decimais no banco
     */
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /**
     * @Min = valor inteiro mínimo
     */
    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    /**
     * @NotBlank = categoria obrigatória
     */
    @NotBlank(message = "A categoria é obrigatória")
    @Column(nullable = false, length = 50)
    private String categoria;

    /**
     * Registra automaticamente quando o produto foi criado.
     * updatable = false: este campo nunca é atualizado após a criação
     */
    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    /**
     * Registra a última atualização do produto.
     */
    private LocalDateTime dataAtualizacao;

    // ========== CONSTRUTORES ==========

    /**
     * Construtor padrão (obrigatório para o JPA funcionar)
     */
    public Produto() {
    }

    /**
     * Construtor com todos os campos principais
     */
    public Produto(String nome, String descricao, BigDecimal preco,
                   Integer quantidadeEstoque, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    // ========== MÉTODOS DO CICLO DE VIDA JPA ==========

    /**
     * @PrePersist = executado ANTES de salvar no banco pela primeira vez
     */
    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * @PreUpdate = executado ANTES de atualizar no banco
     */
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // ========== GETTERS E SETTERS ==========
    // Em POO, getters e setters controlam o acesso aos atributos privados (encapsulamento)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    // ========== toString ==========
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
