package br.com.plutusmanager.PlutusManager.controller;

import br.com.plutusmanager.PlutusManager.entities.Pedido;
import br.com.plutusmanager.PlutusManager.entities.Produto;
import br.com.plutusmanager.PlutusManager.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        List<Pedido> pedidos = pedidoService.findAll();
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Pedido> create(@RequestBody Pedido pedido) {
        try {
            Pedido newPedido = pedidoService.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{pedidoId}/item")
    public ResponseEntity<Pedido> addItemToPedido(@PathVariable Long pedidoId, @RequestBody Produto produto, @RequestParam Integer quantidade) {
        try {
            Pedido pedido = pedidoService.addItemToPedido(pedidoId, produto, quantidade);
            return ResponseEntity.ok().body(pedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        try {
            Pedido updatePedido = pedidoService.update(id,pedidoDetails);
            return ResponseEntity.ok().body(updatePedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!pedidoService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        pedidoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{pedidoId}/item/{itemId}")
    public ResponseEntity<Pedido> deleteItemFromPedido(@PathVariable Long pedidoId, @PathVariable Long itemId) {
        try {
            Pedido pedido = pedidoService.deleteItemFromPedido(pedidoId, itemId);
            return ResponseEntity.ok().body(pedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
