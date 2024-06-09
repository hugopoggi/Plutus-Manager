package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Pessoa;
import br.com.plutusmanager.PlutusManager.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findById(UUID id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa findByNome(String nome) {
        return pessoaRepository.findByNome(nome);
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void deleteById(UUID id) {
        pessoaRepository.deleteById(id);
    }
    
    public Pessoa update(UUID id, Pessoa pessoaDetails) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        if (optionalPessoa.isPresent()) {
            Pessoa existingPessoa = optionalPessoa.get();
            existingPessoa.setNome(pessoaDetails.getNome());
            existingPessoa.setTelefone(pessoaDetails.getTelefone());
            existingPessoa.setEmail(pessoaDetails.getEmail());
            return pessoaRepository.save(existingPessoa);
        } else {
            throw new RuntimeException("Id do cliente não encontrado: " + id);
        }
    }
}
