package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Categoria;
import br.com.plutusmanager.PlutusManager.entities.Produto;
import br.com.plutusmanager.PlutusManager.repository.CategoriaRepository;
import br.com.plutusmanager.PlutusManager.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> findByCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaCategoriaId(categoriaId);
    }

    public Produto findByDescricao(String descricao) {
        return produtoRepository.findByDescricao(descricao);
    }

    public Produto save(Produto produto, long categoriaId) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaId);
        if (optionalCategoria.isPresent()) {
            produto.setCategoria(optionalCategoria.get());
            return produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Id da categoria não encontrado: " + categoriaId);
        }
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto update(Long id, Produto produtoDetails) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto existingProduto = optionalProduto.get();
            existingProduto.setDescricao(produtoDetails.getDescricao());
            existingProduto.setCusto(produtoDetails.getCusto());
            existingProduto.setPreco(produtoDetails.getPreco());
            existingProduto.setCategoria(produtoDetails.getCategoria());
            return produtoRepository.save(existingProduto);
        } else {
            throw new RuntimeException("Id do pedido não encontrado: " + produtoDetails.getProdutoId());
        }
    }
}
