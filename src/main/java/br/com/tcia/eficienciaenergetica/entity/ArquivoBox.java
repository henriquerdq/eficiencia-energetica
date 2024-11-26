package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import br.com.tcia.eficienciaenergetica.utils.ConverterUtil;

@Log4j2
@Data
@Entity
@Table(name = "arquivo_box")
public class ArquivoBox {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arquivo_box_seq")
    @SequenceGenerator(name = "arquivo_box_seq", sequenceName = "arquivo_box_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario")
    private UsuarioIdentificacao usuario;

    @Column(name = "ano_mes")
    private String anoMes;

    @Column(name = "dt_instalacao", columnDefinition = "DATE")
    private LocalDate dataInstalacao;

    @Column(name = "nm_perfil_cliente")
    private String perfilCliente;

    @Column(name = "nm_canal_venda_grupo")
    private String canalVendaGrupo;

    @Column(name = "regional_claro")
    private String regionalClaro;

    @Column(name = "modelo_stb")
    private String modelo;

    @Column(name = "nm_tech")
    private String tech;

    @Column(name = "streaming")
    private Integer streaming;

    @Column(name = "churn")
    private Integer churn;

    @Column(name = "fx_1_aud")
    private String fx1Aud;

    @Column(name = "dias_1_aud")
    private Integer dias1Aud;

    @Column(name = "qtd")
    private Integer qtd;

    @Column(name = "dt_inclusao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataInclusao;
    
    public Integer addDado(Long numLinha, String... valor) {
		if (valor != null) {
			int i = 0;
			try {
				//chave composta que vem do arquivo
				setAnoMes(valor[i++].trim());
				setDataInstalacao(ConverterUtil.converterStringEmData(valor[i++].trim()));
				setPerfilCliente(ConverterUtil.extrairString(valor[i++].trim()));
				setCanalVendaGrupo(ConverterUtil.extrairString(valor[i++].trim()));
				setRegionalClaro(ConverterUtil.extrairString(valor[i++].trim()));
				setModelo(ConverterUtil.extrairString(valor[i++].trim()));
				setTech(ConverterUtil.extrairString(valor[i++].trim()));
				setStreaming(ConverterUtil.converterStringEmInteger(valor[i++].trim()));
				setChurn(ConverterUtil.converterStringEmInteger(valor[i++].trim()));
				setDias1Aud(ConverterUtil.converterStringEmInteger(valor[i++].trim()));
				setFx1Aud(ConverterUtil.extrairString(valor[i++].trim()));
				setQtd(ConverterUtil.converterStringEmInteger(valor[i++].trim()));
			} catch (Exception e) {
				log.info(e.getLocalizedMessage() + " Erro dado ArquivoBox. " + numLinha + " ## " + Arrays.toString(valor));
				return 1;
			}
		}
		return 0;
	}
}
