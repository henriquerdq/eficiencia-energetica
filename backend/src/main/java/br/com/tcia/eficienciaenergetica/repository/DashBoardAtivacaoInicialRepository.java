package br.com.tcia.eficienciaenergetica.repository;

import br.com.tcia.eficienciaenergetica.entity.DashBoardAtivacaoInicial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashBoardAtivacaoInicialRepository extends JpaRepository<DashBoardAtivacaoInicial, Long> {
    List<DashBoardAtivacaoInicial> findByAnoMes(String anoMes);
}
