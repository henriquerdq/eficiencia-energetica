package br.com.tcia.eficienciaenergetica.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource("classpath:config.properties")
public class AppConfigProperties {

	@Value("${url_site}")
	private String urlSite;

    @Value("${acessar_o_sistema}")
    private String acessarSistema;

	@Value("${empresa}")
	private String empresa;

	@Value("${cadastro_msg}")
	private String cadastroMsg;

	@Value("${ativacao_msg}")
	private String ativacaoMsg;

	@Value("${recuperar_senha}")
	private String recuperarSenha;

	@Value("${entre_com_codigo_acima}")
	private String entreComCodigoAcima;

	@Value("${acesse_msg}")
	private String acesseMsg;

	@Value("${solicitacao_emitida}")
	private String solicitacaoEmitida;

	@Value("${desativacao_msg}")
	private String desativacaoMsg;

}
