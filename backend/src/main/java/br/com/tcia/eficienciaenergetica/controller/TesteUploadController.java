package br.com.tcia.eficienciaenergetica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(value = "/teste-upload")
public class TesteUploadController {
	

	@GetMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam MultipartFile arquivo, @RequestParam String nome) throws Exception {
		return ResponseEntity.ok(nome);
	}

}
