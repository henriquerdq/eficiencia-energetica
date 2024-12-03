package br.com.tcia.eficienciaenergetica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tcia.eficienciaenergetica.entity.Usuario;
import br.com.tcia.eficienciaenergetica.repository.UsuarioRepository;
import br.com.tcia.eficienciaenergetica.security.AccessToken;
import br.com.tcia.eficienciaenergetica.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;

    @Transactional
    public Usuario buscarPorId(Long id) {
    	return usuarioRepository.findById(id).orElseThrow();
    }
    
    @Transactional
    public Usuario salvar(Usuario usuario) {
    	return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void deletar(Long id) {
    	usuarioRepository.deleteById(id);
    }

    public Page<Usuario> buscarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }


    public Usuario findByEmail(String email) {
    	return usuarioRepository.findByEmail(email);
    }

	public AccessToken autenticar(String email, String senha) {
		var usuario = findByEmail(email);
		boolean senhaValida = passwordEncoder.matches(senha, usuario.getSenha());
		if(senhaValida) {
			return jwtTokenService.gerarToken(usuario);
		}
		return null;
	}
    
    public String codificarSenha(String senha) {
    	return passwordEncoder.encode(senha);
    }

}
