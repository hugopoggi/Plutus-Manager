package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Categoria;
import br.com.plutusmanager.PlutusManager.repository.CategoriaRepository;
import br.com.plutusmanager.PlutusManager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id){
        return categoriaRepository.findById(id);
    }

    public Categoria save (Categoria categoria){
        Long usuarioId = categoria.getUsuario().getUsuarioId();
        categoria.setUsuario(usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id){
        categoriaRepository.deleteById(id);
    }

    public Categoria update(Long id, Categoria categoriaDetails){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if(optionalCategoria.isPresent()){
            Categoria existingCategoria = optionalCategoria.get();
            existingCategoria.setDescricao(categoriaDetails.getDescricao());
            return categoriaRepository.save(existingCategoria);
        } else {
            throw new RuntimeException("Id categoria não encontrado: " + id);
        }
    }
}
