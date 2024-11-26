package br.com.tcia.eficienciaenergetica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;


@Log
@RestController
@RequestMapping(value = "upload-arquivo-tv-box")
public class UploadArquivoTvBoxController {

	@GetMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam MultipartFile arquivo,
			@RequestParam String nome) {
		log.info(arquivo.getContentType());
		return ResponseEntity.ok().build();
	}

}
