package br.com.tcia.eficienciaenergetica.exceptions;

public class NegocioException extends SistemaException{

	private static final long serialVersionUID = 1L;
	private String mensagem;

	public NegocioException(String mensagem) {
		super(mensagem);
		this.mensagem = mensagem;
	}
	
	public NegocioException(Throwable excecao) {
		super(excecao);
	}

	public NegocioException(String mensagem, Throwable excecao) {
		super(mensagem, excecao);
		this.mensagem = mensagem;
	}

	public NegocioException(String mensagem, Object[] parametros, Throwable excecao) {
		super(mensagem, parametros, excecao);
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

}
