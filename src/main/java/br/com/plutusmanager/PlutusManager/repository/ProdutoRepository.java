package br.com.plutusmanager.PlutusManager.repository;

import br.com.plutusmanager.PlutusManager.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}