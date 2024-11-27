package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends JpaRepository<Token, String> {
    void deleteByDataCriacaoBefore(LocalDateTime limite);
}
