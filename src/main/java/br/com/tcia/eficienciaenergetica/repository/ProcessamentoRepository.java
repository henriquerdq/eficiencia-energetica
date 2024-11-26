package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.Processamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProcessamentoRepository extends JpaRepository<Processamento, Long> {
    List<Processamento> findByDataStartBetween(LocalDateTime start, LocalDateTime end);
}
