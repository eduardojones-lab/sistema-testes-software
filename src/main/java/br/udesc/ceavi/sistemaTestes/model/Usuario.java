package br.udesc.ceavi.sistemaTestes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * ENTIDADE: Usuario
 *
 * Representa um usuário do sistema.
 * Utilizada para demonstrar testes de segurança no Postman.
 *
 * Conceitos de OO aplicados:
 *   - Encapsulamento: atributos privados com getters/setters
 *   - Abstração: a classe representa o conceito de "Usuário" do mundo real
 */
@Entity
@Table(name = "usuarios",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "email", name = "uk_usuario_email")
       })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nome;

    /**
     * @Email = valida o formato do email (ex: usuario@dominio.com)
     * unique = true: não pode ter dois usuários com o mesmo email
     */
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    /**
     * A senha deve ter no mínimo 8 caracteres.
     * Em um sistema real, a senha seria armazenada com hash (ex: BCrypt)
     */
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Column(nullable = false)
    private String senha;

    /**
     * Papel do usuário: ADMIN ou USER
     * Serve para demonstrar controle de acesso nos testes de segurança
     */
    @Column(nullable = false, length = 20)
    private String perfil = "USER";

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    // ========== CONSTRUTORES ==========

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    // ========== CICLO DE VIDA JPA ==========

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // ========== GETTERS E SETTERS ==========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
