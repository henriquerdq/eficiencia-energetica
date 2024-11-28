package br.com.tcia.eficienciaenergetica.controller.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsuarioResponse implements Serializable {
	
	private static final long serialVersionUID = -493537153537630851L;
	
    private Long id;
    private String email;
    private String senha;
    private String nome;
    private Boolean ativo;
    private Boolean cadastroConfirmado;
    private String idConfirmacaoCadastro;
    private Long idPerfil;

}
