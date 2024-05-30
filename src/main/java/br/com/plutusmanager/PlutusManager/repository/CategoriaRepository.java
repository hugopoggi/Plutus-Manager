package br.com.plutusmanager.PlutusManager.repository;

import br.com.plutusmanager.PlutusManager.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
