package br.com.tcia.eficienciaenergetica.controller;

import java.util.stream.Collectors;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcia.eficienciaenergetica.controller.assemble.UsuarioModelAssembler;
import br.com.tcia.eficienciaenergetica.controller.mapper.UsuarioMapper;
import br.com.tcia.eficienciaenergetica.controller.request.UsuarioRequest;
import br.com.tcia.eficienciaenergetica.controller.response.UsuarioResponse;
import br.com.tcia.eficienciaenergetica.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final UsuarioMapper mapper;
	private final UsuarioModelAssembler assembler;

	private PagedResourcesAssembler<EntityModel<UsuarioResponse>> assemblerPage;

	@GetMapping(value = "/{id}")
	public EntityModel<UsuarioResponse> findById(@PathVariable(required = true) Long id) {
		var conta = usuarioService.buscarPorId(id);
		return assembler.toModel(conta);
	}

	@GetMapping
	public ResponseEntity<?> findAll(Pageable pageable) {

		var usuarios = usuarioService.buscarTodos(pageable);

		var lista = usuarios.stream().map(usuario -> assembler.toModel(usuario)).collect(Collectors.toList());

		var listaPage = new PageImpl<EntityModel<UsuarioResponse>>(lista, usuarios.getPageable(),
				usuarios.getTotalElements());

		return ResponseEntity.ok(assemblerPage.toModel(listaPage));
	}

	@PostMapping
	public EntityModel<UsuarioResponse> incluir(@Valid @RequestBody UsuarioRequest request) {
		var usuario = usuarioService.salvar(mapper.toUsuario(request));
		return assembler.toModel(usuario);
	}

	@PutMapping
	public EntityModel<UsuarioResponse> alterar(@Valid @RequestBody UsuarioRequest request) {
		var usuario = usuarioService.salvar(mapper.toUsuario(request));
		return assembler.toModel(usuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(required = true) Long id) {
		usuarioService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
