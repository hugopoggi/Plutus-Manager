package br.com.plutusmanager.PlutusManager.service;

import br.com.plutusmanager.PlutusManager.entities.Pedido;
import br.com.plutusmanager.PlutusManager.entities.PedidoItem;
import br.com.plutusmanager.PlutusManager.entities.Produto;
import br.com.plutusmanager.PlutusManager.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
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
            pedidoItem.setProduto(produto);
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
}
