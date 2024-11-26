package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menu_perfil")
public class MenuPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_perfil_seq")
    @SequenceGenerator(name = "menu_perfil_seq", sequenceName = "menu_perfil_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;
}
