package br.udesc.ceavi.sistemaTestes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * TRATADOR GLOBAL DE EXCEÇÕES: GlobalExceptionHandler
 *
 * @RestControllerAdvice = intercepta exceções lançadas em qualquer Controller
 * e retorna respostas HTTP adequadas ao invés de erros 500 genéricos.
 *
 * Isso é fundamental para os testes de USABILIDADE:
 * o usuário da API recebe mensagens claras sobre o que deu errado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata RecursoNaoEncontradoException → retorna HTTP 404 Not Found
     */
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex) {

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", 404);
        erro.put("erro", "Recurso não encontrado");
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /**
     * Trata erros de validação (@NotBlank, @Email, etc.) → retorna HTTP 400 Bad Request
     *
     * Este é o tratamento de VALIDAÇÃO DE CAMPOS.
     * Quando o Postman enviar dados inválidos, o retorno será descritivo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacaoErros(
            MethodArgumentNotValidException ex) {

        Map<String, String> errosCampos = new HashMap<>();

        // Para cada campo com erro, coleta o nome do campo e a mensagem de erro
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            errosCampos.put(campo, mensagem);
        });

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now().toString());
        resposta.put("status", 400);
        resposta.put("erro", "Erro de validação");
        resposta.put("campos", errosCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    /**
     * Trata erros de email duplicado → retorna HTTP 409 Conflict
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleArgumentoInvalido(
            IllegalArgumentException ex) {

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", 409);
        erro.put("erro", "Conflito de dados");
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    /**
     * Trata qualquer outra exceção não prevista → retorna HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleErroGeral(Exception ex) {

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now().toString());
        erro.put("status", 500);
        erro.put("erro", "Erro interno do servidor");
        erro.put("mensagem", "Ocorreu um erro inesperado. Contate o suporte.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
