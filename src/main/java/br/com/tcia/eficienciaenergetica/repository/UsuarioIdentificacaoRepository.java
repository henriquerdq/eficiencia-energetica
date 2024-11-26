package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.UsuarioIdentificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioIdentificacaoRepository extends JpaRepository<UsuarioIdentificacao, Long> {
    Optional<UsuarioIdentificacao> findByEmail(String email);
}
