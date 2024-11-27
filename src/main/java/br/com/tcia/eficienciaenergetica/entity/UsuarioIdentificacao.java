package br.com.tcia.eficienciaenergetica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id_fk", nullable = false)
    private Perfil perfil;
}
