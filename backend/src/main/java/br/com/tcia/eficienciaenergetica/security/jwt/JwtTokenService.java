package br.com.tcia.eficienciaenergetica.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import br.com.tcia.eficienciaenergetica.entity.Usuario;
import br.com.tcia.eficienciaenergetica.security.AccessToken;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenService {
	
	private SecretKey key;
	
	@PostConstruct
	public void secretKeyGenerator() {
		if(Objects.isNull(key)) {
			key = Jwts.SIG.HS256.key().build();
		}
	}

	public AccessToken gerarToken(Usuario usuario) {
		var accessToken = Jwts
				.builder()
				.signWith(key)
				.claim("perfil", usuario.getPerfil().getNome())
				.subject(usuario.getEmail())
				.expiration(gerarDataExpiracao())
				.compact();
		return AccessToken.builder().accessToken(accessToken).build();
	}
	
	private Date gerarDataExpiracao() {
		var dataHora = LocalDateTime.now().plusMinutes(60);
		return Date.from(dataHora.atZone(ZoneId.systemDefault()).toInstant());
	}
	
}
