package br.com.tcia.eficienciaenergetica.email;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class HtmlTableGenerator {

	private static final String COR_F7F7F7 = "F7F7F7";
	private static final String COR_CCCCCC = "CCCCCC";
	private static final String COR_FFFFFF = "FFFFFF";
	private String tituloTabela;
	private String rodapeTabela;
	private String corAtual = COR_FFFFFF;
	private LinkedHashMap<String, Object> dados;
	private boolean temRodape = true;
	
	@Value("${url_site}")
	private String urlSite;

	@Value("${empresa2}")
	private String empresa;

	public String montaTabela() {
		StringBuilder html = new StringBuilder();
		html.append(getCabecalho());
		html.append(tituloTabela);
		html.append(montaLinhas());
		if (temRodape) {
			html.append(getRodapeTabela());
		}
		html.append(getFimTabela());
		return html.toString();
	}

	public String montaTabelaSemRodape() {
		StringBuilder html = new StringBuilder();
		html.append(getCabecalho());
		html.append(tituloTabela);
		html.append(montaLinhas());
		return html.toString();
	}

	private String montaLinhas() {
		String ret = "";
		for (String key : dados.keySet()) {
			Object value = dados.get(key);
			if (Objects.nonNull(value)) {
				String linhaNova = getLinha();
				linhaNova = linhaNova.replace("{1}", key);
				linhaNova = linhaNova.replace("{2}", value.toString());
				if (corAtual.equalsIgnoreCase(COR_FFFFFF)) {
					linhaNova = linhaNova.replace("{3}", COR_FFFFFF);
					linhaNova = linhaNova.replace("{4}", COR_FFFFFF);
					corAtual = COR_F7F7F7;
				} else {
					linhaNova = linhaNova.replace("{3}", COR_F7F7F7);
					linhaNova = linhaNova.replace("{4}", COR_F7F7F7);
					corAtual = COR_FFFFFF;
				}
				ret += linhaNova;
			} else {
				String linhaNova = getLinhaMesclada();
				linhaNova = linhaNova.replace("{1}", key);
				linhaNova = linhaNova.replace("{3}", COR_CCCCCC);
				ret += linhaNova;

			}

		}
		return ret;
	}

	public Map<String, Object> getDados() {
		if (Objects.isNull(dados)) {
			dados = new LinkedHashMap<String, Object>();
		}
		return dados;
	}

	public void setDados(LinkedHashMap<String, Object> dados) {
		this.dados = dados;
	}

	public String getCabecalho() {
		StringBuilder html = new StringBuilder();
		html.append("<html lang=\"pt-BR\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");
		html.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#");
		html.append(COR_CCCCCC);
		html.append("\"><tr>");
		html.append("<td><table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"1\">");
		return html.toString();
	}

	private String getLinha() {
		StringBuilder html = new StringBuilder();
		html.append("<tr><td bgcolor=\"#{3}\" width='15%' style=\"margin:10px\" align=\"right\"><span align=\"right\" nowrap=\"nowrap\"><strong>{1}</strong></span></td>");
		html.append("<td bgcolor=\"#{4}\" align=\"left\"><span align=\"left\" style=\"margin:10px\">{2}</span></td></tr>");
		return html.toString();
	}

	private String getLinhaMesclada() {
		StringBuilder html = new StringBuilder();
		html.append("<tr>");
		html.append("<td colspan=\"2\" bgcolor=\"#{3}\" style=\"margin:10px\" align=\"center\"><span align=\"center\" nowrap=\"nowrap\"><strong>{1}</strong></span></td>");
		html.append("</tr>");
		return html.toString();
	}

	public void setTituloTabelaSemNomeEmpresa(String msg) {
		StringBuilder html = new StringBuilder();
		html.append("<tr align=\"center\"><td colspan='2' align=\"center\"><span align=\"center\" style=\"font-size:16px\"><strong>");
		html.append(msg);
		html.append("</strong></span></td></tr>");
		tituloTabela = html.toString();
	}

	public void setTituloTabela(String msg) {
		StringBuilder html = new StringBuilder();
		html.append("<tr align=\"center\"><td align=\"center\">");
		html.append("<img src=\"");
		html.append(urlSite);
		html.append("/javax.faces.resource/images/logo_tcia.png.html?ln=barcelona-layout\" alt=\"");
		html.append(empresa);
		html.append("\" width=\"120\"/></td>");
		html.append("<td><span align=\"center\"><strong>");
		html.append(msg);
		html.append("</strong></span></td></tr>");
		tituloTabela = html.toString();
	}

	public void setRodapeTabela(String msg) {
		if (Objects.nonNull(msg)) {
			temRodape = true;
			StringBuilder html = new StringBuilder();
			html.append("<tr><td bgcolor=\"#");
			html.append(corAtual);
			html.append("\" colspan=\"2\"><div align=\"center\">");
			html.append(msg);
			html.append("<br><b>Não responda esse e-mail.</b></div></td></tr>");
			rodapeTabela = html.toString();
		} else {
			rodapeTabela = null;
		}
	}

	public void setRodapeTabelaSemAviso(String msg) {
		if (msg != null) {
			temRodape = true;
			StringBuilder html = new StringBuilder();
			html.append("<tr><td bgcolor=\"#");
			html.append(corAtual);
			html.append("\" colspan=\"2\"><div align=\"center\">");
			html.append(msg);
			html.append("</div></td></tr>");

			rodapeTabela = html.toString();
		} else {
			rodapeTabela = null;
		}
	}

	public String getRodapeTabela() {
		if (!(Objects.nonNull(this.rodapeTabela) && !"".equalsIgnoreCase(this.rodapeTabela))) {
			setRodapeTabela();
		}
		return this.rodapeTabela;
	}

	public void setRodapeTabela() {
		StringBuilder html = new StringBuilder();
		html.append("<tr><td bgcolor=\"#");
		html.append(corAtual);
		html.append("\" colspan=\"2\"><div align=\"center\"><br><b>ATENÇÃO: Não responda esse e-mail.</b></div></td></tr>");
		rodapeTabela = html.toString();
	}

	private String getFimTabela() {
		return "</table></td></tr></table></body></html>";
	}
	
}

