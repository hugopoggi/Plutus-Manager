package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.dto.LoginRequestDto;
import br.com.plutusmanager.PlutusManager.entities.Usuario;
import br.com.plutusmanager.PlutusManager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public UUID findByLoginAndPassword(LoginRequestDto loginRequest) {
        Usuario usuario = usuarioRepository.findByLoginUsuarioAndSenha(loginRequest.getLoginUsuario(), loginRequest.getSenha());
        if (usuario != null) {
            return usuario.getUsuarioId();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuarioDetails) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario existingUsuario = optionalUsuario.get();
            existingUsuario.setLoginUsuario(usuarioDetails.getLoginUsuario());
            existingUsuario.setNomeUsuario(usuarioDetails.getNomeUsuario());
            existingUsuario.setSenha(usuarioDetails.getSenha());
            existingUsuario.setEmail(usuarioDetails.getEmail());
            existingUsuario.setCpf(usuarioDetails.getCpf());
            existingUsuario.setCep(usuarioDetails.getCep());
            existingUsuario.setRua(usuarioDetails.getRua());
            existingUsuario.setBairro(usuarioDetails.getBairro());
            existingUsuario.setNumeroResidencia(usuarioDetails.getNumeroResidencia());
            existingUsuario.setTelefone(usuarioDetails.getTelefone());
            existingUsuario.setCidade(usuarioDetails.getCidade());
            existingUsuario.setPais(usuarioDetails.getPais());
            existingUsuario.setEstado(usuarioDetails.getEstado());
            existingUsuario.setDataNascimento(usuarioDetails.getDataNascimento());
            existingUsuario.setVencimento(usuarioDetails.getVencimento());
            return usuarioRepository.save(existingUsuario);
        } else {
            throw new RuntimeException("Usuario não encontrado por ID " + id);
        }
    }
}
