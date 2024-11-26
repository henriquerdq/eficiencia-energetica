package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.DashBoardAtivacaoInicial;
import br.com.tcia.eficienciaenergetica.repository.DashBoardAtivacaoInicialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardAtivacaoInicialService {

    private final DashBoardAtivacaoInicialRepository repository;

    public List<DashBoardAtivacaoInicial> buscarTodos() {
        return repository.findAll();
    }

    public List<DashBoardAtivacaoInicial> buscarPorAnoMes(String anoMes) {
        return repository.findByAnoMes(anoMes);
    }

    public DashBoardAtivacaoInicial salvar(DashBoardAtivacaoInicial dashboard) {
        return repository.save(dashboard);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
