package br.udesc.ceavi.sistemaTestes.controller;

import br.udesc.ceavi.sistemaTestes.model.Usuario;
import br.udesc.ceavi.sistemaTestes.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CONTROLADOR: UsuarioController
 *
 * Endpoints para gerenciamento de usuários.
 * Inclui rota de login para demonstrar testes de SEGURANÇA.
 *
 * URL base: /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ================================================
    // GET /api/usuarios
    // Lista todos os usuários
    // ================================================
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // ================================================
    // GET /api/usuarios/{id}
    // ================================================
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // ================================================
    // POST /api/usuarios
    // Cadastra novo usuário
    // ================================================
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // ================================================
    // PUT /api/usuarios/{id}
    // ================================================
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    // ================================================
    // DELETE /api/usuarios/{id}
    // ================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ================================================
    // POST /api/usuarios/login
    // Endpoint de autenticação - demonstra testes de SEGURANÇA
    //
    // Corpo esperado:
    // {
    //   "email": "usuario@email.com",
    //   "senha": "minhasenha123"
    // }
    // ================================================
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        // Validação básica dos campos
        if (email == null || email.isBlank()) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", "O campo 'email' é obrigatório");
            return ResponseEntity.badRequest().body(erro);
        }

        if (senha == null || senha.isBlank()) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("erro", "O campo 'senha' é obrigatório");
            return ResponseEntity.badRequest().body(erro);
        }

        Map<String, Object> resultado = usuarioService.login(email, senha);
        return ResponseEntity.ok(resultado);
    }

    // ================================================
    // GET /api/usuarios/count
    // Conta usuários (útil para testes de desempenho)
    // ================================================
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> contarUsuarios() {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("total", usuarioService.contarUsuarios());
        return ResponseEntity.ok(resposta);
    }
}
