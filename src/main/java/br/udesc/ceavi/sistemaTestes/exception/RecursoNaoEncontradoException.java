package br.udesc.ceavi.sistemaTestes.exception;

/**
 * EXCEÇÃO PERSONALIZADA: RecursoNaoEncontradoException
 *
 * Em OO, podemos criar nossas próprias exceções para representar
 * situações específicas do nosso domínio.
 *
 * Herda de RuntimeException (exceção não verificada).
 * Será lançada quando buscarmos por um ID que não existe no banco.
 *
 * Exemplo de uso:
 *   throw new RecursoNaoEncontradoException("Produto não encontrado com ID: " + id);
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    private final String recurso;
    private final Long id;

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(recurso + " não encontrado(a) com ID: " + id);
        this.recurso = recurso;
        this.id = id;
    }

    public String getRecurso() { return recurso; }
    public Long getId() { return id; }
}
