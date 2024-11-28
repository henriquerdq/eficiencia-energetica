package br.com.tcia.eficienciaenergetica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tcia.eficienciaenergetica.entity.Usuario;

public interface UsuarioIdentificacaoRepository extends JpaRepository<Usuario, Long> {
}
