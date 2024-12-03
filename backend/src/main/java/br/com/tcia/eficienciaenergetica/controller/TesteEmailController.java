package br.com.tcia.eficienciaenergetica.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcia.eficienciaenergetica.service.EmailService;
import br.com.tcia.eficienciaenergetica.service.ProcessamentoService;
import br.com.tcia.eficienciaenergetica.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(value = "/teste-email")
public class TesteEmailController {
	
	private final EmailService emailService;
	private final UsuarioService usuarioService;
	private final ProcessamentoService processamentoService;
	

	@GetMapping("/enviar")
	public ResponseEntity<String> enviar() throws Exception {
		
		var usuario = usuarioService.buscarPorId(2l);
		var processamento = processamentoService.buscarTodosProcessamentos().get(1);
		
		emailService.enviaEmailCadastroAutorizado(usuario);
		emailService.enviaEmailCadastroUsuario(usuario, List.of(usuario));
		emailService.enviaEmailCadastroUsuarioAdm(usuario, List.of(usuario));
		emailService.enviaEmailErroCSVGenerico(usuario, "enviaEmailErroCSVGenerico");
		emailService.enviaEmailErroCSVSerializado("enviaEmailErroCSVSerializado", "testando o Email", usuario, "enviaEmailErroCSVSerializado", 
				List.of("1","2","3"), LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
		emailService.enviaEmailRecuperacaoSenhaUsuario(usuario);
		emailService.enviaEmailRecuperaSenha(usuario);
		emailService.enviaEmailUsuarioAtivado(usuario);
		emailService.enviaEmailUsuarioDesativado(usuario);
		emailService.enviarEmailProcessamentoRealizado(processamento, "enviarEmailProcessamentoRealizado", 
				LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
		
		return ResponseEntity.ok().build();
	}

}
