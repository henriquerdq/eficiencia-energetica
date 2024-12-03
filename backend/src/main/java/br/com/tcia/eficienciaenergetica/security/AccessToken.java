package br.com.tcia.eficienciaenergetica.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessToken {

	private String accessToken;
	
}