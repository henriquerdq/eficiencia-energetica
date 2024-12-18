package br.com.tcia.eficienciaenergetica.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.tcia.eficienciaenergetica.config.AppConfigProperties;
import br.com.tcia.eficienciaenergetica.email.EmailAdapter;
import br.com.tcia.eficienciaenergetica.email.HtmlTableGenerator;
import br.com.tcia.eficienciaenergetica.entity.Processamento;
import br.com.tcia.eficienciaenergetica.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailAdapter emailAdapter;
    private final AppConfigProperties appConfig;

	public void enviaEmailCadastroAutorizado(Usuario usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Confirmação de cadastro " + appConfig.getEmpresa() + ".<br/>" + "Acesso liberado." + appConfig.getUrlSite() + ".");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());
		htg.getDados().put("Senha", usuario.getSenha());
		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(), appConfig.getCadastroMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEmailCadastroUsuarioAdm(Usuario usuario, List<Usuario> adms) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Novo pedido de acesso ao sistema dashboardClaro.<br/>" + "Entre no sistema para avaliar o pedido." + appConfig.getUrlSite() + ".");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		for (Usuario ui : adms) {
			emailAdapter.enviarEmail(appConfig.getEmpresa(), ui.getEmail(),
					appConfig.getCadastroMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
		}
	}

	public void enviaEmailCadastroUsuario(Usuario usuario, List<Usuario> adms) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Você fez um pedido de cadastro " + appConfig.getEmpresa() + ".<br/>"
				+ "Aguarde a liberação do seu cadastro por um de nossos administradores.<br/>" + appConfig.getUrlSite() + ".");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				 appConfig.getCadastroMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEmailRecuperacaoSenhaUsuario(Usuario usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();

		String urlAltera = appConfig.getUrlSite() + "/paginas/alterar_senha.html";

		htg.setTituloTabela(appConfig.getSolicitacaoEmitida() + " <a href=\"" + appConfig.getUrlSite() + "\">TCIA</a>.");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());
		htg.getDados().put("Cód. recuperação", usuario.getIdConfirmacaoCadastro());
		htg.setRodapeTabela(appConfig.getAcesseMsg() + " <a href=\"" + urlAltera + "\">" + urlAltera + "</a>" + " " +  appConfig.getEntreComCodigoAcima());

		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				appConfig.getRecuperarSenha() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEmailUsuarioDesativado(Usuario usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Cadastro desativado");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela(null);
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				appConfig.getDesativacaoMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEmailUsuarioAtivado(Usuario usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Cadastro ativado");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				appConfig.getAtivacaoMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEmailRecuperaSenha(Usuario usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Sua senha");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());
		htg.getDados().put("Senha", usuario.getSenha());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				"Recuperação de senha - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviarEmailProcessamentoRealizado(Processamento p, String assunto, LocalDateTime dataINI, LocalDateTime dataFIM) {
		var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("pt-BR"));
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Processamento de arquivo realizado.");
		if (dataINI != null) {
			htg.getDados().put("Início às", dataINI.format(sdf));
		} else {
			htg.getDados().put("Início às", "N/A");
		}
		if (dataFIM != null) {
			htg.getDados().put("Término às", dataFIM.format(sdf));
		} else {
			htg.getDados().put("Término às", "N/A");
		}
		htg.getDados().put("Nome arquivo", p.getNomeArquivo());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), p.getUsuario().getEmail(), assunto, htg.montaTabela());
	}

	public void enviaEmailErroCSVSerializado(String assunto, String descricao, Usuario dest, String nomeArq, List<String> linhasComErro, LocalDateTime dataINI, LocalDateTime dataFIM) {
		var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.forLanguageTag("pt-BR"));
		
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Sua planilha possui muitas linhas inválidas.");
		htg.getDados().put("Usuário", dest.getNome());
		htg.getDados().put("Email", dest.getEmail());
		htg.getDados().put("Arquivo", nomeArq);
		if(dataINI != null) {
			htg.getDados().put("Início às", dataINI.format(sdf));
		} else {
			htg.getDados().put("Início às", "N/A");
		}
		if(dataFIM != null) {
			htg.getDados().put("Término às", dataFIM.format(sdf));
		} else {
			htg.getDados().put("Término às", "N/A");
		}
		htg.getDados().put("Descrição", descricao);
		htg.getDados().put("Linhas erro", getLinhas(linhasComErro));
		htg.setRodapeTabela("ATENÇÃO: Este erro não evitou o tratamento do arquivo. Tente realizar um novo upload o mais rápido possível");
		
		emailAdapter.enviarEmail(appConfig.getEmpresa(), dest.getEmail(), "Erro " + assunto, htg.montaTabela());
	}
	
	public void enviaEmailErroCSVGenerico(Usuario dest, String nomeArq) {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Aconteceu um erro não previsto na importação do arquivo");
		htg.getDados().put("Usuário", dest.getNome());
		htg.getDados().put("Email", dest.getEmail());
		htg.getDados().put("Arquivo", nomeArq);
		htg.setRodapeTabela("ATENÇÃO: Este erro só pode ser visto pela equipe de manutenção. Entre em contato e repasse esse e-mail aos mesmos.");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), dest.getEmail(), "Erro genérico", htg.montaTabela());
	}
	
	private String getLinhas(List<String> linhasComErro) {
	    Objects.requireNonNull(linhasComErro, "A lista de linhas com erro não pode ser nula.");

	    var ret = new StringBuilder();
	    int i = 0;

	    for (String linha : linhasComErro) {
	        if (Objects.isNull(linha) || !linha.contains(";")) {
	            continue;
	        }

	        int separadorIndex = linha.indexOf(";");
	        String numeroLinha = linha.substring(0, separadorIndex);
	        String conteudoLinha = linha.substring(separadorIndex + 1);

	        ret.append("Linha: %s - Conteúdo: %s<br/>".formatted(numeroLinha, conteudoLinha));
	        i++;

	        if (i >= 100) {
	            ret.append("... (Exibindo apenas as 100 primeiras linhas)<br/>");
	            break;
	        }
	    }

	    return ret.toString();
	}

   
}
