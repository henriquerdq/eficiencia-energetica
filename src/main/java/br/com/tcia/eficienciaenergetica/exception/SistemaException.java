package br.com.tcia.eficienciaenergetica.exception;

import lombok.Getter;

@Getter
public class SistemaException extends Exception{

	private static final long serialVersionUID = 1L;

	private final String tituloErro = "Erro";
	private final String tituloAlerta = "Alerta";
	private final String tituloInfo = "Atenção";
	private String mensagem;
	
	public SistemaException(String chave) {
		super(chave);
		mensagem = chave;
	}

	public SistemaException(Throwable e) {
		super(e);
		mensagem = tituloErro;
	}

	public SistemaException(String chave, Throwable e) {
		super((chave), e);
		mensagem = chave;
	}

	public SistemaException(String chave, Object[] parametros, Throwable e) {
		super((chave), e);
		mensagem = chave;
	}
	
}
