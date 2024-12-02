package br.com.tcia.eficienciaenergetica.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tcia.eficienciaenergetica.service.EmailService;
import br.com.tcia.eficienciaenergetica.service.ProcessamentoService;
import br.com.tcia.eficienciaenergetica.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(value = "teste-upload")
public class TesteUploadController {
	
	private final EmailService EmailService;
	private final UsuarioService usuarioService;
	private final ProcessamentoService processamentoService;
	

	@GetMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam MultipartFile arquivo, @RequestParam String nome) throws Exception {
		
		var usuario = usuarioService.buscarPorId(1l);
		var processamento = processamentoService.buscarTodosProcessamentos().get(0);
		
		EmailService.enviaEmailCadastroAutorizado(usuario);
		EmailService.enviaEmailCadastroUsuario(usuario, List.of(usuario));
		EmailService.enviaEmailCadastroUsuarioAdm(usuario, List.of(usuario));
		EmailService.enviaEmailErroCSVGenerico(usuario, nome);
		EmailService.enviaEmailErroCSVSerializado("enviaEmailErroCSVSerializado", "testando o Email", usuario, nome, List.of("1","2","3"), LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
		EmailService.enviaEmailRecuperacaoSenhaUsuario(usuario);
		EmailService.enviaEmailRecuperaSenha(usuario);
		EmailService.enviaEmailUsuarioAtivado(usuario);
		EmailService.enviaEmailUsuarioDesativado(usuario);
		EmailService.enviarEmailProcessamentoRealizado(processamento, "enviarEmailProcessamentoRealizado", LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
		
		return ResponseEntity.ok().build();
	}

}
