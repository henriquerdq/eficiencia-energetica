package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Dominio;
import br.com.tcia.eficienciaenergetica.repository.DominioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DadosAuxiliaresService {

    private final DominioRepository dominioRepository;

    public List<Dominio> buscarDominiosPorChave(String chave) {
        return dominioRepository.findByDominiosChave(chave);
    }

    public Optional<Dominio> buscarDominioPorValor(String valor) {
        return dominioRepository.findByDominiosValor(valor);
    }

    public Dominio salvarDominio(Dominio dominio) {
        return dominioRepository.save(dominio);
    }

    public void removerDominio(Long id) {
        dominioRepository.deleteById(id);
    }
}
