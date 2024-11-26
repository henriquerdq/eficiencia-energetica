package br.com.tcia.eficienciaenergetica.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import br.com.tcia.eficienciaenergetica.config.AppConfigProperties;
import br.com.tcia.eficienciaenergetica.email.EmailAdapter;
import br.com.tcia.eficienciaenergetica.email.HtmlTableGenerator;
import br.com.tcia.eficienciaenergetica.entity.Processamento;
import br.com.tcia.eficienciaenergetica.entity.UsuarioIdentificacao;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailAdapter emailAdapter;
    private final AppConfigProperties appConfig;


	public void enviaEMailCadastroAutorizado(UsuarioIdentificacao usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Confirmação de cadastro " + appConfig.getEmpresa() + ".<br/>" + "Acesso liberado." + appConfig.getUrlSite() + ".");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());
		htg.getDados().put("Senha", usuario.getSenha());
		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(), appConfig.getCadastroMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEMailCadastroUsuarioAdm(UsuarioIdentificacao usuario, List<UsuarioIdentificacao> adms) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Novo pedido de acesso ao sistema dashboardClaro.<br/>" + "Entre no sistema para avaliar o pedido." + appConfig.getUrlSite() + ".");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		for (UsuarioIdentificacao ui : adms) {
			emailAdapter.enviarEmail(appConfig.getEmpresa(), ui.getEmail(),
					appConfig.getCadastroMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
		}
	}

	public void enviaEMailCadastroUsuario(UsuarioIdentificacao usuario, List<UsuarioIdentificacao> adms) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Você fez um pedido de cadastro " + appConfig.getEmpresa() + ".<br/>"
				+ "Aguarde a liberação do seu cadastro por um de nossos administradores.<br/>" + appConfig.getUrlSite() + ".");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				 appConfig.getCadastroMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaMensagemErro(String mensagem, StackTraceElement[] stack, List<String> emails) {
		try {
			var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
			var sdfH = DateTimeFormatter.ofPattern("HH:MM:ss", Locale.forLanguageTag("pt-BR"));

			HtmlTableGenerator htg = new HtmlTableGenerator();
			htg.setTituloTabela("Erro de sistema.");
			htg.getDados().put("Data", LocalDate.now().format(sdf));
			htg.getDados().put("Hora", LocalDateTime.now().format(sdfH));
			htg.getDados().put("Mensagem", mensagem);
			if (stack != null) {
				htg.getDados().put("Stack", "&nbsp;");
				String envio = "";
				for (int i = 0; i < stack.length; i++) {
					StackTraceElement stackTraceElement = stack[i];
					envio += "Classe: " + stackTraceElement.getClassName() + " &nbsp;&nbsp;-&nbsp;&nbsp;";
					envio += "Método: " + stackTraceElement.getMethodName() + " &nbsp;&nbsp;-&nbsp;&nbsp;";
					envio += "Linha: " + stackTraceElement.getLineNumber() + "<br/>";
				}
				htg.getDados().put("&nbsp;", envio);
			}
			htg.setRodapeTabela("&nbsp;");

			for (Iterator<String> iterator = emails.iterator(); iterator.hasNext();) {
				String email = (String) iterator.next();

				emailAdapter.enviarEmail(appConfig.getEmpresa(), email, " Erro - " + LocalDateTime.now().format(sdf),
						htg.montaTabela());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviaMensagemErroUnico(String mensagem, StackTraceElement[] stack, UsuarioIdentificacao usr) {
		try {
			var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
			var sdfH = DateTimeFormatter.ofPattern("HH:MM:ss", Locale.forLanguageTag("pt-BR"));

			HtmlTableGenerator htg = new HtmlTableGenerator();
			htg.setTituloTabela("Erro de sistema.");
			htg.getDados().put("Data", LocalDate.now().format(sdf));
			htg.getDados().put("Hora", LocalDateTime.now().format(sdfH));
			htg.getDados().put("Mensagem", mensagem);
			if (stack != null) {
				htg.getDados().put("Stack", "&nbsp;");
				String envio = "";
				for (int i = 0; i < stack.length; i++) {
					StackTraceElement stackTraceElement = stack[i];
					envio += "Classe: " + stackTraceElement.getClassName() + " &nbsp;&nbsp;-&nbsp;&nbsp;";
					envio += "Método: " + stackTraceElement.getMethodName() + " &nbsp;&nbsp;-&nbsp;&nbsp;";
					envio += "Linha: " + stackTraceElement.getLineNumber() + "<br/>";
				}
				htg.getDados().put("&nbsp;", envio);
			}
			htg.setRodapeTabela("&nbsp;");

			if (usr != null) {
				emailAdapter.enviarEmail(appConfig.getEmpresa(), usr.getEmail(), " Erro - " + LocalDate.now().format(sdf), htg.montaTabela());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviaMensagemErro(String titulo, String mensagem, UsuarioIdentificacao usr) {
		try {
			var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
			var sdfH = DateTimeFormatter.ofPattern("HH:MM:ss", Locale.forLanguageTag("pt-BR"));

			HtmlTableGenerator htg = new HtmlTableGenerator();
			htg.setTituloTabela(titulo);
			htg.getDados().put("Data", LocalDate.now().format(sdf));
			htg.getDados().put("Hora", LocalDateTime.now().format(sdfH));
			htg.getDados().put("Mensagem", mensagem);
			htg.setRodapeTabela("&nbsp;");

			if (usr != null) {
				emailAdapter.enviarEmail(appConfig.getEmpresa(), usr.getEmail(), " Erro - " + LocalDate.now().format(sdf), htg.montaTabela());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviaMensagemErro(String mensagem, String email) {
		try {
			var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
			var sdfH = DateTimeFormatter.ofPattern("HH:MM:ss", Locale.forLanguageTag("pt-BR"));

			HtmlTableGenerator htg = new HtmlTableGenerator();
			htg.setTituloTabela("Erro de sistema.");
			htg.getDados().put("Data", LocalDate.now().format(sdf));
			htg.getDados().put("Hora", LocalDateTime.now().format(sdfH));
			htg.getDados().put("Mensagem", mensagem);
			htg.setRodapeTabela("&nbsp;");

			emailAdapter.enviarEmail(appConfig.getEmpresa(), email, " Erro - " + LocalDate.now().format(sdf), htg.montaTabela());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviaEMailRecuperacaoSenhaUsuario(UsuarioIdentificacao usuario) throws Exception {
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

	public void enviaEMailUsuarioDesativado(UsuarioIdentificacao usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Cadastro desativado");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela(null);
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				appConfig.getDesativacaoMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEMailUsuarioAtivado(UsuarioIdentificacao usuario) throws Exception {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Cadastro ativado");
		htg.getDados().put("Login", usuario.getEmail());
		htg.getDados().put("Nome", usuario.getNome());

		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">" + appConfig.getAcessarSistema() + "</a><br/>");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), usuario.getEmail(),
				appConfig.getAtivacaoMsg() + " - " + appConfig.getEmpresa(), htg.montaTabela());
	}

	public void enviaEMailRecuperaSenha(UsuarioIdentificacao usuario) throws Exception {
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

	public void enviaEMailErroCSVSerializado(String assunto, String descricao, UsuarioIdentificacao dest, String nomeArq, List<String> linhasComErro, LocalDateTime dataINI, LocalDateTime dataFIM) {
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
	
	public void enviaEMailErroCSVGenerico(UsuarioIdentificacao dest, String nomeArq) {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Aconteceu um erro não previsto na importação do arquivo");
		htg.getDados().put("Usuário", dest.getNome());
		htg.getDados().put("Email", dest.getEmail());
		htg.getDados().put("Arquivo", nomeArq);
		htg.setRodapeTabela("ATENÇÃO: Este erro só pode ser visto pela equipe de manutenção. Entre em contato e repasse esse e-mail aos mesmos.");
		emailAdapter.enviarEmail(appConfig.getEmpresa(), dest.getEmail(), "Erro genérico", htg.montaTabela());
	}
	
	private String getLinhas(List<String> linhasComErro) {
		int i = 0;
		StringBuilder ret = new StringBuilder("");
		if(linhasComErro != null) {
			for(String d : linhasComErro) {
				ret.append("Linha: " + d.substring(0, d.indexOf(";")) + " -Conteúdo: " + d.substring(d.indexOf(";") + 1, d.length()) + "<br/>");
				i++;
				if(i > 100) {
					break;
				}
			}
		}
		return ret.toString();
	}

	public void enviaEMailErroPlanilha(String cabecalho, UsuarioIdentificacao dest) throws UnsupportedEncodingException, MessagingException {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Erro no arquivo enviado.");
		htg.getDados().put("Descrição", cabecalho);
		htg.getDados().put("", htg);
		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">Acessar o sistema</a><br/>");
		if (dest != null) {
			emailAdapter.enviarEmail(appConfig.getEmpresa(), dest.getEmail(), "Resumo processamento", htg.montaTabela());
		}
	}

	public void enviaEMailProcessamentoPlanilhaBatimento(Integer qtdItens, String tamanho, String nomeGerado, String resultado, UsuarioIdentificacao dest, 
			StringBuilder cacheLog, String tipo, List<String> seriais)
			throws UnsupportedEncodingException, MessagingException {
		HtmlTableGenerator htg = new HtmlTableGenerator();
		htg.setTituloTabela("Resumo da carga de arquivo " + tipo.toUpperCase());
		
		htg.getDados().put("Qtd itens", qtdItens);
		htg.getDados().put("Tamanho do arquivo", tamanho);
		htg.getDados().put("Nome do arquivo", nomeGerado);
		htg.getDados().put("Resultado", resultado);

		
		if(seriais != null && !seriais.isEmpty()) {
			String ser = "";
			for(String s : seriais) {
				ser += s + ", ";
			}
			
			htg.getDados().put("Seriais nao inseridos", ser.substring(0, ser.length() - 2));
		}
		
		if(cacheLog != null && cacheLog.length() > 0) {
			htg.getDados().put("Linhas com erro", cacheLog.toString());
		}
		
		htg.setRodapeTabela("<a href=\"" + appConfig.getUrlSite() + "\">Acessar o sistema</a><br/>");
		
		if (dest != null) {
			emailAdapter.enviarEmail(appConfig.getEmpresa(), dest.getEmail(), "Processamento " + tipo, htg.montaTabela());
		}
	}
   
}
