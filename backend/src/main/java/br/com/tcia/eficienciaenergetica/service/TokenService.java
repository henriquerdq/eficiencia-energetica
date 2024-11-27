package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.Token;
import br.com.tcia.eficienciaenergetica.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Optional<Token> buscarPorToken(String token) {
        return tokenRepository.findById(token);
    }

    public Token salvarToken(Token token) {
        return tokenRepository.save(token);
    }

    public void excluirTokensAntigos(LocalDateTime limite) {
        tokenRepository.deleteByDataCriacaoBefore(limite);
    }
}
