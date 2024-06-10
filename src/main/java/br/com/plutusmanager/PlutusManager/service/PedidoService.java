package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Pedido;
import br.com.plutusmanager.PlutusManager.entities.PedidoItem;
import br.com.plutusmanager.PlutusManager.entities.Pessoa;
import br.com.plutusmanager.PlutusManager.entities.Produto;
import br.com.plutusmanager.PlutusManager.repository.PedidoRepository;
import br.com.plutusmanager.PlutusManager.repository.PessoaRepository;
import br.com.plutusmanager.PlutusManager.repository.ProdutoRepository;
import br.com.plutusmanager.PlutusManager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {

        UUID usuarioId = pedido.getUsuario().getUsuarioId();
        pedido.setUsuario(usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));

        UUID pessoaId = pedido.getPessoa().getPessoaId();
        pedido.setPessoa(pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrado")));
        /*
        Pessoa pessoa = pedido.getPessoa();
        if(pedido.getFormaDePagamento() == Pedido.FormaDePagamento.CARTAO_CREDITO) {
            BigDecimal valorTotal = calcularValorTotal(pedido);
            if(pessoa.getSaldoDisponivel().compareTo(valorTotal) < 0) {
                throw new RuntimeException("Saldo insuficiente para realizar a compra");
            }
        }
        Pedido savedPedido = pedidoRepository.save(pedido);
        if(pedido.getFormaDePagamento() == Pedido.FormaDePagamento.CARTAO_CREDITO) {
            ajustarSaldoDisponivel(pessoa, savedPedido);
        }

        return savedPedido;
         */

        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido update(Long id, Pedido pedidoDetails) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isPresent()) {
            Pedido existingPedido = optionalPedido.get();
            existingPedido.setDataPedido(pedidoDetails.getDataPedido());
            existingPedido.setFormaDePagamento(pedidoDetails.getFormaDePagamento());
            existingPedido.setStatus(pedidoDetails.getStatus());
            return pedidoRepository.save(existingPedido);
        } else {
            throw new RuntimeException("Id do pedido não encontrado: " + pedidoDetails.getPedidoId());
        }
    }

    public Pedido addItemToPedido(Long pedidoId, Produto produto, Integer quantidade) {

        Optional<Pedido> optionalPedido = pedidoRepository.findById(pedidoId);
        if (optionalPedido.isPresent()) {
            Pedido pedido = optionalPedido.get();
            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setPedido(pedido);

            Long produtoId = produto.getProdutoId();
            pedidoItem.setProduto(produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado")));

            pedidoItem.setQuantidade(quantidade);
            pedido.getItens().add(pedidoItem);
            return pedidoRepository.save(pedido);

        } else {
            throw new RuntimeException("Pedido não encontrado: " + pedidoId);
        }
    }

    public Pedido deleteItemFromPedido(Long pedidoId, Long itemId) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(pedidoId);
        if (optionalPedido.isPresent()) {
            Pedido pedido = optionalPedido.get();
            pedido.getItens().removeIf(item -> item.getId().equals(itemId));
            return pedidoRepository.save(pedido);
        } else {
            throw new RuntimeException("Pedido não encontrado: " + pedidoId);
        }
    }

    private BigDecimal calcularValorTotal(Pedido pedido) {
        return pedido.getItens().stream()
                .map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void ajustarSaldoDisponivel(Pessoa pessoa, Pedido pedido) {
        BigDecimal valorTotal = calcularValorTotal(pedido);
        pessoa.setSaldoDisponivel(pessoa.getSaldoDisponivel().subtract(valorTotal));
        pessoaRepository.save(pessoa);
    }
}
