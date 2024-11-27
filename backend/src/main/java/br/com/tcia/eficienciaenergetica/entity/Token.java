package br.com.tcia.eficienciaenergetica.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
