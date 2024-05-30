package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Categoria;
import br.com.plutusmanager.PlutusManager.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id){
        return categoriaRepository.findById(id);
    }

    public Categoria save (Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id){
        categoriaRepository.deleteById(id);
    }

    public Categoria update(Long id, Categoria categoriaDetails){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaDetails.getCategoriaId());
        if(optionalCategoria.isPresent()){
            Categoria existingCategoria = optionalCategoria.get();
            existingCategoria.setDescricao(categoriaDetails.getDescricao());
            return categoriaRepository.save(existingCategoria);
        } else {
            throw new RuntimeException("Id categoria não encontrado: " + id);
        }
    }
}
