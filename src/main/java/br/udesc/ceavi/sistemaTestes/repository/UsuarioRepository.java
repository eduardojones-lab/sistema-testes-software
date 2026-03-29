package br.udesc.ceavi.sistemaTestes.repository;

import br.udesc.ceavi.sistemaTestes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITÓRIO: UsuarioRepository
 *
 * Interface responsável pelo acesso ao banco para a entidade Usuario.
 * Optional<T> representa um valor que pode ou não estar presente,
 * evitando NullPointerException.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuário pelo email.
     * Optional evita NullPointerException: em vez de retornar null,
     * retorna Optional.empty() quando não encontrado.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se já existe um usuário com o email informado.
     * Útil para validar duplicatas antes de cadastrar.
     * → SELECT COUNT(*) > 0 FROM usuarios WHERE email = ?
     */
    boolean existsByEmail(String email);

    /**
     * Busca todos os usuários de um determinado perfil (ADMIN ou USER)
     */
    java.util.List<Usuario> findByPerfil(String perfil);
}
