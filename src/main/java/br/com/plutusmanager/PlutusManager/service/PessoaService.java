package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Pessoa;
import br.com.plutusmanager.PlutusManager.repository.PessoaRepository;
import br.com.plutusmanager.PlutusManager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findById(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa findByNome(String nome) {
        return pessoaRepository.findByNome(nome);
    }

    public Pessoa save(Pessoa pessoa) {
        Long usuarioId = pessoa.getUsuario().getUsuarioId();
        pessoa.setUsuario(usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        return pessoaRepository.save(pessoa);
    }

    public void deleteById(Long id) {
        pessoaRepository.deleteById(id);
    }
    
    public Pessoa update(Long id, Pessoa pessoaDetails) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        if (optionalPessoa.isPresent()) {
            Pessoa existingPessoa = optionalPessoa.get();
            existingPessoa.setNome(pessoaDetails.getNome());
            existingPessoa.setCpf(pessoaDetails.getCpf());
            existingPessoa.setEmail(pessoaDetails.getEmail());
            existingPessoa.setTelefone(pessoaDetails.getTelefone());
            existingPessoa.setSaldoDisponivel(pessoaDetails.getSaldoDisponivel());
            existingPessoa.setCep(pessoaDetails.getCep());
            existingPessoa.setRua(pessoaDetails.getRua());
            existingPessoa.setBairro(pessoaDetails.getBairro());
            existingPessoa.setNumeroResidencia(pessoaDetails.getNumeroResidencia());
            existingPessoa.setCidade(pessoaDetails.getCidade());
            existingPessoa.setEstado(pessoaDetails.getEstado());
            existingPessoa.setPais(pessoaDetails.getPais());
            return pessoaRepository.save(existingPessoa);
        } else {
            throw new RuntimeException("Id do cliente não encontrado: " + id);
        }
    }
}
