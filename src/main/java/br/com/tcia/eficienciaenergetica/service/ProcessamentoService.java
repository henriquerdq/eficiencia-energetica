package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Processamento;
import br.com.tcia.eficienciaenergetica.repository.ProcessamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessamentoService {

    private final ProcessamentoRepository processamentoRepository;

    public List<Processamento> buscarTodosProcessamentos() {
        return processamentoRepository.findAll();
    }

    public Processamento salvarProcessamento(Processamento processamento) {
        return processamentoRepository.save(processamento);
    }

    public void excluirProcessamento(Long id) {
        processamentoRepository.deleteById(id);
    }
}
