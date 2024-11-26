package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Perfil;
import br.com.tcia.eficienciaenergetica.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public List<Perfil> buscarTodosPerfis() {
        return perfilRepository.findAll();
    }

    public Perfil salvarPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    public void excluirPerfil(Long id) {
        perfilRepository.deleteById(id);
    }
}
