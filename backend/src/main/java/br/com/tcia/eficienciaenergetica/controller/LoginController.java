package br.com.tcia.eficienciaenergetica.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcia.eficienciaenergetica.controller.request.LoginRequest;
import br.com.tcia.eficienciaenergetica.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(value = "/autenticar")
public class LoginController {
	
	private final UsuarioService usuarioService;
	

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest login) throws Exception {
		var token = usuarioService.autenticar(login.getEmail(), login.getSenha());
		if(Objects.isNull(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(token.getAccessToken());
	}

}
