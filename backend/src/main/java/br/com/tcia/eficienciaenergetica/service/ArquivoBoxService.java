package br.com.tcia.eficienciaenergetica.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.tcia.eficienciaenergetica.entity.ArquivoBox;
import br.com.tcia.eficienciaenergetica.entity.Processamento;
import br.com.tcia.eficienciaenergetica.enums.ProcessamentoTipoEnum;
import br.com.tcia.eficienciaenergetica.exception.SistemaException;
import br.com.tcia.eficienciaenergetica.repository.ArquivoBoxRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class ArquivoBoxService extends AbstractImportacaoArquivo {

    private final EmailService emailService;
    private final ArquivoBoxRepository arquivoBoxRepository;

    private static final String ARQUIVO_BOX = "Arquivo BOX";

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void tratarArquivo(Processamento processamento, ProcessamentoTipoEnum tipoProcessamento) throws SistemaException {
        LocalDateTime dataInicio = LocalDateTime.now();
        List<String> linhasComErro = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(new File(processamento.getArquivoAProcessar()));
             Scanner scanner = new Scanner(inputStream, adiquirirCharsetDoArquivo(new File(processamento.getArquivoAProcessar())))) {

            processarArquivo(processamento, tipoProcessamento, scanner, linhasComErro, dataInicio);
        } catch (Exception ex) {
            log.error("Erro no tratamento %s".formatted(ARQUIVO_BOX), ex);
            emailService.enviaEmailErroCSVGenerico(processamento.getUsuario(), processamento.getNomeArquivo());
            throw new SistemaException(ex);
        }
    }

    private void processarArquivo(Processamento processamento, ProcessamentoTipoEnum tipoProcessamento, Scanner scanner,
                                  List<String> linhasComErro, LocalDateTime dataInicio) throws SistemaException {
        long linha = 0;
        List<ArquivoBox> relatorio = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String linhaAtual = removeUTF8BOM(scanner.nextLine());

            if (linha == 0) {
                validarCabecalhoInicial(processamento, tipoProcessamento, linhaAtual, linhasComErro, dataInicio);
            } else {
                processarLinha(tipoProcessamento, linha, linhaAtual, relatorio, linhasComErro);
            }

            if (linhasComErro.size() > MAX_ERROS_PERMITIDOS) {
                break;
            }
            linha++;
        }

        salvarRelatorio(relatorio);
        finalizarProcessamento(processamento, linhasComErro, dataInicio);
    }

    private void validarCabecalhoInicial(Processamento processamento, ProcessamentoTipoEnum tipoProcessamento,
                                         String linhaCabecalho, List<String> linhasComErro, LocalDateTime dataInicio) throws SistemaException {
        if (!validarCabecalhoPontoVirgula(linhaCabecalho, tipoProcessamento.getStringValidacao())) {
            linhasComErro.add(linhaCabecalho);
            enviarEmailErroCabecalho(processamento, linhasComErro, dataInicio);
            throw new SistemaException(ARQUIVO_COM_CABECALHO_INVALIDO_PONTO_VIRGULA + " ### " + linhaCabecalho);
        }
        arquivoBoxRepository.deleteAll();
    }

    private void processarLinha(ProcessamentoTipoEnum tipoProcessamento, long numeroLinha, String linha,
                                List<ArquivoBox> relatorio, List<String> linhasComErro) {
        String[] dadosLinha = extrairArrayPontoVirgula(linha, tipoProcessamento);

        if (Objects.nonNull(dadosLinha)) {
            ArquivoBox arquivoBox = new ArquivoBox();
            Integer resultado = arquivoBox.addDado(numeroLinha, dadosLinha);

            if (resultado == 0) {
                relatorio.add(arquivoBox);
                if (relatorio.size() > LIMITE_LINHAS_INSERIR) {
                    salvarRelatorio(relatorio);
                }
            } else {
                linhasComErro.add(numeroLinha + ";" + linha);
            }
        }
    }

    private void salvarRelatorio(List<ArquivoBox> relatorio) {
        if (!relatorio.isEmpty()) {
            arquivoBoxRepository.saveAll(relatorio);
            relatorio.clear();
        }
    }

    private void finalizarProcessamento(Processamento processamento, List<String> linhasComErro, LocalDateTime dataInicio) throws SistemaException {
        LocalDateTime dataFim = LocalDateTime.now();

        if (!processamento.getReprocessar()) {
            if (!linhasComErro.isEmpty()) {
                emailService. enviaEmailErroCSVSerializado(ARQUIVO_BOX, ARQUIVO_COM_MUITAS_LINHAS_INVALIDAS, processamento.getUsuario(),
                        processamento.getNomeArquivo(), linhasComErro, dataInicio, dataFim);

                if (linhasComErro.size() > MAX_ERROS_PERMITIDOS) {
                    throw new SistemaException(ARQUIVO_COM_MUITAS_LINHAS_INVALIDAS);
                }
            } else {
                emailService.enviarEmailProcessamentoRealizado(processamento, ARQUIVO_BOX, dataInicio, dataFim);
            }
        }
    }

    private void enviarEmailErroCabecalho(Processamento processamento, List<String> linhasComErro, LocalDateTime dataInicio) {
        emailService.enviaEmailErroCSVSerializado(ARQUIVO_BOX, ARQUIVO_COM_CABECALHO_INVALIDO_PONTO_VIRGULA,
                processamento.getUsuario(), processamento.getNomeArquivo(), linhasComErro, dataInicio, null);
    }
}
