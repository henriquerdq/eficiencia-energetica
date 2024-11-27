package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
