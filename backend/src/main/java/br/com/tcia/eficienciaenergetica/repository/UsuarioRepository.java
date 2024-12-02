package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
    Optional<Usuario> findByEmail(String email);
    
}
