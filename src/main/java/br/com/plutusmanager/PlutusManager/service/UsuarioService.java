package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Usuario;
import br.com.plutusmanager.PlutusManager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(UUID id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(UUID id, Usuario usuarioDetails) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario existingUsuario = optionalUsuario.get();
            existingUsuario.setLoginUsuario(usuarioDetails.getLoginUsuario());
            existingUsuario.setNomeUsuario(usuarioDetails.getNomeUsuario());
            existingUsuario.setSenha(usuarioDetails.getSenha());
            existingUsuario.setEmail(usuarioDetails.getEmail());
            existingUsuario.setCpf(usuarioDetails.getCpf());
            existingUsuario.setCEP(usuarioDetails.getCEP());
            existingUsuario.setRua(usuarioDetails.getRua());
            existingUsuario.setBairro(usuarioDetails.getBairro());
            existingUsuario.setNumero(usuarioDetails.getNumero());
            existingUsuario.setCidade(usuarioDetails.getCidade());
            existingUsuario.setPais(usuarioDetails.getPais());
            existingUsuario.setEstado(usuarioDetails.getEstado());
            existingUsuario.setDataNascimento(usuarioDetails.getDataNascimento());
            existingUsuario.setvencimento(usuarioDetails.getvencimento());
            return usuarioRepository.save(existingUsuario);
        } else {
            throw new RuntimeException("Usuario não encontrado por ID " + id);
        }
    }
}