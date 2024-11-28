package br.com.tcia.eficienciaenergetica.entity;

import java.util.Date;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.tcia.eficienciaenergetica.enums.ProcessamentoTipoEnum;
import br.com.tcia.eficienciaenergetica.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="processamento")
public class Processamento {

	@Id
	@SequenceGenerator(name = "processamento_seq", sequenceName = "processamento_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processamento_seq")
	@Column(unique = true, nullable = false, name="id")
	private Long id;

	@Column(name = "data_start")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataStart;

	@Column(name = "data_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name = "data_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@Column(name = "tipo_processamento")
	private Integer tipoProcessamento;
	
	@Column(name = "executado")
	private Boolean executado;
	
	@Column(name = "reprocessar")
	private Boolean reprocessar;
	
	@Column(name = "qtd_reprocessar")
	private Integer qtdReprocessar;
	
	@Column(name = "qtd_reprocessado")
	private Integer qtdReprocessado;
	
	@Column(name = "resultado")
	private String resultado;
	
	@Column(name = "resultado_amigavel", length = 200)
	private String resultadoAmigavel;
	
	@Column(name = "parametro", length = 200)
	private String parametro;
	
	@Column(name = "arquivo_a_processar", length = 200)
	private String arquivoAProcessar;
	
	@Column(name = "arquivo_processado", length = 200)
	private String arquivoProcessado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "usuario")
	private Usuario usuario;
	
	public String getDescricaoProcessamento() {
		if(tipoProcessamento != null) {
			return ProcessamentoTipoEnum.getNomePeloID(tipoProcessamento);
		}
		return "";
	}

	public String getNomeArquivo() {
		if(arquivoAProcessar != null) {
			return Utils.extrairNomeParametro(arquivoAProcessar);
		}
		return "";
	}
	
	public String getArquivoAtual() {
		return getArquivoProcessado() == null ? getArquivoAProcessar() : getArquivoProcessado();
	}
	
	public String getTamanhoArquivo() {
		if(parametro != null) {
			return parametro.split("#")[1];
		}
		return "";
	}
	
	public Boolean getExibeBTCancelar() {
		if(!executado) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Boolean getExibeBTReexecutar() {
		if(executado && "SUCESSO".equals(resultadoAmigavel)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
