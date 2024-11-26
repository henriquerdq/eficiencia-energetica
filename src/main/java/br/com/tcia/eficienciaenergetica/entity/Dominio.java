package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dominio")
public class Dominio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dominio_seq")
    @SequenceGenerator(name = "dominio_seq", sequenceName = "dominio_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(name = "dominios_chave", nullable = false)
    private String dominiosChave;

    @Column(name = "dominios_valor", nullable = false)
    private String dominiosValor;
}
