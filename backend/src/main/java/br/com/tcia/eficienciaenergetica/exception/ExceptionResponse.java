package br.com.tcia.eficienciaenergetica.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDateTime dataHora;
	private String mensagem;
	private String erro;
	
}
