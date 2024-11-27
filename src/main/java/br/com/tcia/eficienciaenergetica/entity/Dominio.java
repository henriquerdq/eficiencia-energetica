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

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
