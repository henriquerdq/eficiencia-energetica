package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Menu;
import br.com.tcia.eficienciaenergetica.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<Menu> buscarTodosMenus() {
        return menuRepository.findAll();
    }

    public Menu salvarMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public void excluirMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
