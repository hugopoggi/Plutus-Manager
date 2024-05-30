package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Produto;
import br.com.plutusmanager.PlutusManager.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto update(Long id, Produto produtoDetails) {
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoDetails.getProdutoId());
        if (optionalProduto.isPresent()) {
            Produto existingProduto = optionalProduto.get();
            existingProduto.setDescricao(produtoDetails.getDescricao());
            existingProduto.setCusto(produtoDetails.getCusto());
            existingProduto.setPreco(produtoDetails.getPreco());
            return produtoRepository.save(existingProduto);
        } else {
            throw new RuntimeException("Id do pedido não encontrado: " + produtoDetails.getProdutoId());
        }
    }
}
