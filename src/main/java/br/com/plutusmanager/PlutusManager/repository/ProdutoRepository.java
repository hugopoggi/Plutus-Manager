package br.com.plutusmanager.PlutusManager.repository;

import br.com.plutusmanager.PlutusManager.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaCategoriaId(Long categoriaId);

    Produto findByDescricao(String descricao);
}