package br.com.tcia.eficienciaenergetica.controller.assemble;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.tcia.eficienciaenergetica.controller.UsuarioController;
import br.com.tcia.eficienciaenergetica.controller.mapper.UsuarioMapper;
import br.com.tcia.eficienciaenergetica.controller.response.UsuarioResponse;
import br.com.tcia.eficienciaenergetica.entity.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<UsuarioResponse>> {

	@Autowired
	private UsuarioMapper mapper;

	@Override
	public EntityModel<UsuarioResponse> toModel(Usuario usuario) {
		var response = mapper.toUsuarioResponse(usuario);
		return EntityModel.of(response,
				linkTo(methodOn(UsuarioController.class).findById(response.getId())).withSelfRel());
	}

}
