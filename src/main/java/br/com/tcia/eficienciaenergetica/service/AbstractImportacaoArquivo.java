package br.com.tcia.eficienciaenergetica.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.tcia.eficienciaenergetica.enums.ProcessamentoTipoEnum;
import br.com.tcia.eficienciaenergetica.utils.Utils;

public abstract class AbstractImportacaoArquivo {

    protected static final String ARQUIVO_COM_MUITAS_LINHAS_INVALIDAS = "Arquivo com muitas linhas inválidas";
    protected static final String ARQUIVO_COM_CABECALHO_INVALIDO_PONTO_VIRGULA = "Arquivo inválido. Espera-se um cabeçalho com separador (;).";
    protected static final int MAX_ERROS_PERMITIDOS = 5000;
    protected static final int LIMITE_LINHAS_INSERIR = 30000;
    private static final String UTF8_BOM = "\uFEFF";

    protected static String removeUTF8BOM(String linha) {
        return linha != null && linha.startsWith(UTF8_BOM) ? linha.substring(1) : linha;
    }

    protected static String adiquirirCharsetDoArquivo(File file) {
        try (InputStream inputStream = new FileInputStream(file);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {

            while (scanner.hasNextLine()) {
                String linha = removeUTF8BOM(scanner.nextLine());
                if (Utils.contemCaracteresEspeciais(linha)) {
                    return StandardCharsets.UTF_8.displayName();
                }
            }
            return StandardCharsets.ISO_8859_1.displayName();
        } catch (IOException ex) {
            return StandardCharsets.UTF_8.displayName();
        }
    }

    protected boolean validarCabecalhoPontoVirgula(String cabecalho, String cabecalhoEsperado) {
        return validarCabecalho(cabecalho, cabecalhoEsperado, ";");
    }

    protected boolean validarCabecalhoVirgula(String cabecalho, String cabecalhoEsperado) {
        return validarCabecalho(cabecalho, cabecalhoEsperado, ",");
    }

    private boolean validarCabecalho(String cabecalho, String cabecalhoEsperado, String delimitador) {
        String[] partesCabecalho = cabecalho.split(delimitador);
        String[] partesEsperadas = cabecalhoEsperado.split(delimitador);

        if (partesCabecalho.length != partesEsperadas.length) {
            return false;
        }

        return IntStream.range(0, partesCabecalho.length)
                .allMatch(i -> partesEsperadas[i].trim().equalsIgnoreCase(partesCabecalho[i].trim()));
    }

    protected String[] extrairArrayPontoVirgula(String linha, ProcessamentoTipoEnum tipoProcessamento) {
        return extrairArrayLinha(linha, tipoProcessamento, ";");
    }

    protected String[] extrairArrayVirgula(String linha, ProcessamentoTipoEnum tipoProcessamento) {
        return extrairArrayLinha(linha, tipoProcessamento, ",");
    }

    private String[] extrairArrayLinha(String linha, ProcessamentoTipoEnum tipoProcessamento, String delimitador) {
        String[] cabecalhoEsperado = tipoProcessamento.getStringValidacao().split(delimitador);
        String[] colunas = linha.split(delimitador, -1);

        if (cabecalhoEsperado.length != colunas.length) {
            return null;
        }

        long colunasVazias = Stream.of(colunas)
                .limit(Math.min(colunas.length, 4))
                .filter(String::isEmpty)
                .count();

        if (colunasVazias == Math.min(colunas.length, 4)) {
            return null;
        }

        return Stream.of(colunas)
                .map(this::removerAspas)
                .toArray(String[]::new);
    }

    private String removerAspas(String valor) {
        if (valor == null || valor.isEmpty()) {
            return valor;
        }
        if (valor.startsWith("\"")) {
            valor = valor.substring(1);
        }
        if (valor.endsWith("\"")) {
            valor = valor.substring(0, valor.length() - 1);
        }
        return valor;
    }
}
