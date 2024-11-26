package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.Dominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DominioRepository extends JpaRepository<Dominio, Long> {
    List<Dominio> findByDominiosChave(String chave);
    Optional<Dominio> findByDominiosValor(String valor);
}
