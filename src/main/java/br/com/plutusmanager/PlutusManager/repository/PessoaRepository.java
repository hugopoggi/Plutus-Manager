package br.com.plutusmanager.PlutusManager.repository;

import br.com.plutusmanager.PlutusManager.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
    Pessoa findByNome(String nome);
}
