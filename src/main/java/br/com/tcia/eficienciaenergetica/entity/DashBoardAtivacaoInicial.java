package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.*;
import lombok.Data;

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
