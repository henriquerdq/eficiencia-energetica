package br.com.tcia.eficienciaenergetica.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.tcia.eficienciaenergetica.controller.request.UsuarioRequest;
import br.com.tcia.eficienciaenergetica.controller.response.UsuarioResponse;
import br.com.tcia.eficienciaenergetica.entity.Usuario;

@Mapper(componentModel="spring")
public interface UsuarioMapper {

	@Mapping(target = "idPerfil", source = "perfil.id") 
	UsuarioResponse toUsuarioResponse(Usuario usuario);

	@Mapping(target = "perfil.id", source = "idPerfil") 
	Usuario toUsuario(UsuarioRequest request);

}
