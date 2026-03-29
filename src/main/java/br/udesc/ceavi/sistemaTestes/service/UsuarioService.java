package br.udesc.ceavi.sistemaTestes.service;

import br.udesc.ceavi.sistemaTestes.exception.RecursoNaoEncontradoException;
import br.udesc.ceavi.sistemaTestes.model.Usuario;
import br.udesc.ceavi.sistemaTestes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SERVIÇO: UsuarioService
 *
 * Contém a lógica de negócio para gerenciamento de usuários.
 * Inclui validações específicas para demonstrar testes de SEGURANÇA no Postman.
 */
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * CRIAR USUÁRIO
     * Valida se o email já está em uso antes de salvar.
     */
    public Usuario criar(Usuario usuario) {
        // Regra de negócio: email deve ser único
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException(
                "Já existe um usuário cadastrado com o email: " + usuario.getEmail()
            );
        }

        // Em produção real, a senha seria hashed com BCrypt antes de salvar:
        // usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        // Para esta demonstração, salvamos diretamente.

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);

        // Se mudou o email, verifica se o novo email já está em uso por OUTRO usuário
        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
                throw new IllegalArgumentException(
                    "Email já está em uso: " + usuarioAtualizado.getEmail()
                );
            }
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());

        // Só atualiza senha se uma nova foi fornecida
        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isBlank()) {
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }

    /**
     * LOGIN SIMPLES: apenas para demonstração de testes de segurança.
     *
     * ATENÇÃO: Em produção REAL, use Spring Security com JWT!
     * Este método é apenas didático.
     *
     * Retorna um mapa com informações de acesso ou lança exceção.
     */
    @Transactional(readOnly = true)
    public Map<String, Object> login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", 0L));

        // Validação da senha (em produção use BCrypt.matches())
        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta");
        }

        // Simula um "token" de sessão (em produção, seria JWT)
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Login realizado com sucesso");
        resposta.put("usuarioId", usuario.getId());
        resposta.put("nome", usuario.getNome());
        resposta.put("perfil", usuario.getPerfil());
        resposta.put("token", "SIMULADO-TOKEN-" + usuario.getId() + "-" + System.currentTimeMillis());

        return resposta;
    }

    @Transactional(readOnly = true)
    public long contarUsuarios() {
        return usuarioRepository.count();
    }
}
