package br.com.tcia.eficienciaenergetica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ErroAutenticacaoException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;
	
	public ErroAutenticacaoException(String ex) {
		super(ex);
	}
}