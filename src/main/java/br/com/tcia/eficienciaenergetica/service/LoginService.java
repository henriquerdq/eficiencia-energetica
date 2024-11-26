package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Token;
import br.com.tcia.eficienciaenergetica.entity.UsuarioIdentificacao;
import br.com.tcia.eficienciaenergetica.repository.TokenRepository;
import br.com.tcia.eficienciaenergetica.repository.UsuarioIdentificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsuarioIdentificacaoRepository usuarioRepository;
    private final TokenRepository tokenRepository;

    public Optional<UsuarioIdentificacao> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public Token gerarToken(UsuarioIdentificacao usuario) {
        String novoToken = java.util.UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(novoToken);
        token.setUsuario(usuario);
        token.setDataCriacao(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    public boolean validarToken(String token) {
        return tokenRepository.findById(token).isPresent();
    }
}
