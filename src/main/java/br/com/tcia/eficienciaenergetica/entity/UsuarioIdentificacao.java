package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario_identificacao")
public class UsuarioIdentificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_identificacao_seq")
    @SequenceGenerator(name = "usuario_identificacao_seq", sequenceName = "usuario_identificacao_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "cadastro_confirmado", nullable = false)
    private Boolean cadastroConfirmado;

    @Column(name = "id_confirmacao_cadastro")
    private String idConfirmacaoCadastro;

    @Column(name = "ultimo_login", columnDefinition = "TIMESTAMP")
    private LocalDateTime ultimoLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;
}
