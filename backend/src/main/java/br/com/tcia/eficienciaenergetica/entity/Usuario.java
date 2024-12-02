package br.com.tcia.eficienciaenergetica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails, Serializable {

	private static final long serialVersionUID = 3321741296213606231L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
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

    @CreatedDate
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;
	
	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;
	
	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;
    
    public List<String> getRoles() {
		return List.of(perfil.getNome());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(perfil);
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.ativo;
	}
}
