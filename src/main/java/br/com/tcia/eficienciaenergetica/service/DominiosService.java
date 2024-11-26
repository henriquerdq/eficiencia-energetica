package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Dominio;
import br.com.tcia.eficienciaenergetica.repository.DominioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DominiosService {

    private final DominioRepository dominioRepository;

    public List<Dominio> buscarTodosDominios() {
        return dominioRepository.findAll();
    }

    public Dominio buscarDominioPorId(Long id) {
        return dominioRepository.findById(id).orElseThrow(() -> new RuntimeException("Dominio não encontrado."));
    }

    public Dominio salvarDominio(Dominio dominio) {
        return dominioRepository.save(dominio);
    }

    public void excluirDominio(Long id) {
        dominioRepository.deleteById(id);
    }
}
