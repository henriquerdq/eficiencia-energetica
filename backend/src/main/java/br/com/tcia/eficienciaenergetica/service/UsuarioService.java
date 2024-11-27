package br.com.tcia.eficienciaenergetica.service;

import br.com.tcia.eficienciaenergetica.entity.UsuarioIdentificacao;
import br.com.tcia.eficienciaenergetica.repository.UsuarioIdentificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioIdentificacaoRepository usuarioRepository;

    public List<UsuarioIdentificacao> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioIdentificacao> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioIdentificacao salvarUsuario(UsuarioIdentificacao usuario) {
        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
