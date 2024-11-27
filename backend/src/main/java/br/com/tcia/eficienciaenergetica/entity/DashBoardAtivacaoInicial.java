package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "dashboard_ativacao_inicial")
public class DashBoardAtivacaoInicial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_ativacao_seq")
    @SequenceGenerator(name = "dashboard_ativacao_seq", sequenceName = "dashboard_ativacao_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(name = "ano_mes")
    private String anoMes;

    @Column(name = "canal_venda_grupo")
    private String canalVendaGrupo;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "regional_claro")
    private String regionalClaro;

    @Column(name = "qtde_ativacao")
    private Integer qtdeAtivacao;

    @Column(name = "qtde_ativacao_streaming")
    private Integer qtdeAtivacaoStreaming;
}
