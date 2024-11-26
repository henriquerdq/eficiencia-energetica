package br.com.tcia.eficienciaenergetica.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public enum ProcessamentoTipoEnum {

    ARQUIVO_BOX(false, "ANO_MES;DT_INSTALACAO;NM_PERFIL_CLIENTE;NM_CANAL_VENDA_GRUPO;REGIONAL_CLARO;MODELO_STB;NM_TECH;STREAMING;CHURN;DIAS_1_AUD;FX_1_AUD;QTD"),
    POS_ARQUIVO_BOX(false, null, ARQUIVO_BOX),
	INERTE(false, "");
	
	private String stringValidacao;
	private List<Integer> pais;
	private Boolean reprocessarGeral;
	private ProcessamentoTipoEnum(Boolean reprocessarGeral, String nome, ProcessamentoTipoEnum ... pais){
		this.stringValidacao = nome;
		this.pais = new ArrayList<>();
		this.reprocessarGeral = reprocessarGeral;
		if(pais != null && pais.length > 0) {
			for (int i = 0; i < pais.length; i++) {
				this.pais.add(pais[i].ordinal());
			}
		}
	}

	public static String getNomePeloID(int id) {
		for (int i = 0; i < ProcessamentoTipoEnum.values().length; i++) {
			if(ProcessamentoTipoEnum.values()[i].ordinal() == id){
				return ProcessamentoTipoEnum.values()[i].name();
			}
		}
		return null;
	}
	
	public static ProcessamentoTipoEnum getPeloID(int id) {
		for (int i = 0; i < ProcessamentoTipoEnum.values().length; i++) {
			if(ProcessamentoTipoEnum.values()[i].ordinal() == id){
				return ProcessamentoTipoEnum.values()[i];
			}
		}
		return null;
	}
	

	public static List<ProcessamentoTipoEnum> getPocessamentosDependentes(int id) {
		List<ProcessamentoTipoEnum> ret = new ArrayList<>();
		ProcessamentoTipoEnum tipoEntrada = null;
		for (int i = 0; i < ProcessamentoTipoEnum.values().length; i++) {
			if(ProcessamentoTipoEnum.values()[i].ordinal() == id){
				tipoEntrada = ProcessamentoTipoEnum.values()[i];
				break;
			}
		}
		if(tipoEntrada != null) {
			for (int i = 0; i < ProcessamentoTipoEnum.values().length; i++) {
				if(ProcessamentoTipoEnum.values()[i].getPais().contains(Integer.valueOf(tipoEntrada.ordinal()))) {
					ret.add(ProcessamentoTipoEnum.values()[i]);
				}
			}
		}
		
		return ret;
	}
	
	
}
