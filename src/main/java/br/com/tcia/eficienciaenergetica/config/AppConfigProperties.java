package br.com.tcia.eficienciaenergetica.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfigProperties {

    private String urlSite;
    private String empresa;
    private String acessarSistema;
    private String empresaPrincipal;
    private String cadastroMsg;
    private String ativacaoMsg;
    private String recuperarSenha;
    private String entreComCodigoAcima;
    private String acesseMsg;
    private String solicitacaoEmitida;
    private String desativacaoMsg;

}
