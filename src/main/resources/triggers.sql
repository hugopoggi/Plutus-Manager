-- Trigger aciona quando o Pedido tem um item adicionado e é do tipo a prazo (CARTAO_CREDITO)

CREATE TRIGGER verificar_saldo_e_atualizar
    ON tb_pedido_item
    INSTEAD OF INSERT
    AS
BEGIN
    DECLARE @pedido_id BIGINT;
    DECLARE @produto_id BIGINT;
    DECLARE @quantidade INT;
    DECLARE @pessoa_id UNIQUEIDENTIFIER;
    DECLARE @saldo_atual DECIMAL(18, 2);
    DECLARE @preco_total_item DECIMAL(18, 2);
    DECLARE @forma_pagamento VARCHAR(20);

    SELECT @pedido_id = pedido_id, @produto_id = produto_id, @quantidade = quantidade
    FROM inserted;

    -- Obtém a forma de pagamento do pedido
    SELECT @forma_pagamento = forma_de_pagamento, @pessoa_id = pessoa_id
    FROM tb_pedido
    WHERE pedido_id = @pedido_id;

    -- Verifica se a forma de pagamento é CARTAO_CREDITO
    IF @forma_pagamento = 'CARTAO_CREDITO'
        BEGIN
            -- Obtém o saldo atual da pessoa
            SELECT @saldo_atual = saldo_disponivel
            FROM tb_pessoa
            WHERE pessoa_id = @pessoa_id;

            -- Calcula o preço total do item
            SELECT @preco_total_item = preco * @quantidade
            FROM tb_produto
            WHERE produto_id = @produto_id;

            -- Verifica se o saldo é suficiente
            IF @saldo_atual < @preco_total_item
                BEGIN
                    RAISERROR ('Saldo insuficiente para realizar a compra', 16, 1);
                    ROLLBACK;
                    RETURN;
                END
            ELSE
                BEGIN
                    -- Atualiza o saldo disponível da pessoa
                    UPDATE tb_pessoa
                    SET saldo_disponivel = saldo_disponivel - @preco_total_item
                    WHERE pessoa_id = @pessoa_id;

                    -- Insere o item no pedido
                    INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade)
                    SELECT pedido_id, produto_id, quantidade
                    FROM inserted;
                END
        END
    ELSE
        BEGIN
            -- Se não for CARTAO_CREDITO, insere o item normalmente
            INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade)
            SELECT pedido_id, produto_id, quantidade
            FROM inserted;
        END
END;
