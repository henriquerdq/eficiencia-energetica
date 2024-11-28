package br.com.tcia.eficienciaenergetica.controller.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest implements Serializable {
	
	private static final long serialVersionUID = -8757543839784627802L;

	private Long id;
	
	@NotNull(message = "{usuario.nome.not-null}")
	@NotBlank(message = "{usuario.nome.not-blank}")
	@Size(max = 50, message = "{usuario.nome.size}")
	private String nome;
	
    private String email;
    private String senha;
    private Boolean ativo;
    private Boolean cadastroConfirmado;
    private String idConfirmacaoCadastro;
    private Long idPerfil;
	
}
