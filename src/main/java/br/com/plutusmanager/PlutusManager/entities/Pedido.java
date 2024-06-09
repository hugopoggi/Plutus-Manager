package br.com.plutusmanager.PlutusManager.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_Pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id", nullable = false, updatable = false)
    private Long pedidoId;

    @Column(nullable = true)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Column
    private String formaDePagamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public Collection<PedidoItem> getItens() {
        return itens;
    }


    public enum StatusPedido {
        PENDENTE,
        FINALIZADO,
        CANCELADO;
    }


    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    /*
            Quando o fetch type é definido como EAGER, os dados relacionados são carregados imediatamente junto com a entidade principal.
            Isso significa que, ao buscar a entidade principal, todas as entidades relacionadas marcadas como EAGER são também carregadas
            do banco de dados em uma única consulta ou em múltiplas consultas, dependendo da estratégia de implementação.
             */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;
        return pedidoId.equals(pedido.pedidoId);
    }

    @Override
    public int hashCode() {
        return pedidoId.hashCode();
    }
}
