package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "token")
public class Token {

    @Id
    @Column(nullable = false, unique = true, name = "token")
    private String token;

    @Column(name = "id_aplicativo", nullable = false)
    private String idAplicativo;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioIdentificacao usuario;
}
