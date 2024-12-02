package br.com.tcia.eficienciaenergetica.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tcia.eficienciaenergetica.exception.ExceptionResponse;
import br.com.tcia.eficienciaenergetica.exception.NegocioException;

@ControllerAdvice
@RestController
public class ErroResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(
			Exception ex, WebRequest request) {
		
		var exceptionResponse = ExceptionResponse.builder().dataHora(LocalDateTime.now())
				.mensagem(ex.getMessage())
				.erro(request.getDescription(false)).build();
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NegocioException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(
			Exception ex, WebRequest request) {
		
		var exceptionResponse = ExceptionResponse.builder().dataHora(LocalDateTime.now())
				.mensagem(ex.getMessage())
				.erro(request.getDescription(false)).build();
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
