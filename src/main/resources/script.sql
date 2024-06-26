/*-- Inserção para Tabela Usuario
INSERT INTO tb_usuario (login_usuario, nome_usuario, senha, email, cpf, cep, rua, bairro,
                        numero_residencia, telefone, cidade, pais, estado, data_nascimento, vencimento) VALUES
                                                                                                                                                                                          ('mickey', 'Mickey Mouse', 'senha123', 'mickey.mouse@disney.com', '12345678901', '00000-000', 'Rua Disney', 'Magic Kingdom', '123', '123456789', 'Orlando', 'USA', 'FL', '1928-11-18', '2024-12-31'),
                                                                                                                                                                                          ('donald', 'Donald Duck', 'senha123', 'donald.duck@disney.com', '23456789012', '00000-001', 'Rua Macaco', 'Magic Kingdom', '124', '123456780', 'Orlando', 'USA', 'FL', '1934-06-09', '2024-12-31');

-- Inserção para Tabela Categoria
INSERT INTO tb_categoria (descricao, usuario_id)VALUES
                                                                   ('Eletrônicos', (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey')),
                                                                   ('Brinquedos', (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'));


-- Inserção para Tabela Pessoa
INSERT INTO tb_pessoa (nome, cpf, email, telefone, saldo_disponivel, cep, rua, bairro, numero_residencia, cidade, estado, pais, usuario_id) VALUES
                                                                         ('Minnie Mouse', '34567890123', 'minnie.mouse@disney.com', '123456781', 1000.00, '00000-000', 'Rua Disney', 'Magic Kingdom', '123', 'Orlando', 'FL', 'USA', (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey')),
                                                                         ('Daisy Duck', '45678901234', 'daisy.duck@disney.com', '123456782', 100.00, '00000-001', 'Rua Macaco', 'Magic Kingdom', '124', 'Orlando', 'FL', 'USA',  (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'));

-- Inserção para Tabela Produto
INSERT INTO tb_produto (descricao, custo, preco, usuario_id, categoria_id) VALUES
                                                                                          ('Smartphone', 500.00, 700.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Eletrônicos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Minnie Mouse')),
                                                                                          ('Laptop', 800.00, 1200.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Eletrônicos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Minnie Mouse')),
                                                                                          ('Tablet', 300.00, 450.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Eletrônicos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Minnie Mouse')),
                                                                                          ('Headphones', 50.00, 75.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Eletrônicos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Minnie Mouse')),
                                                                                          ('Smartwatch', 150.00, 225.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Eletrônicos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Minnie Mouse')),
                                                                                          ('Carrinho de Brinquedo', 10.00, 15.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Brinquedos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Daisy Duck')),
                                                                                          ('Boneca', 15.00, 25.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Brinquedos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Daisy Duck')),
                                                                                          ('Quebra-cabeça', 8.00, 12.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Brinquedos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Daisy Duck')),
                                                                                          ('Jogo de Tabuleiro', 20.00, 30.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Brinquedos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Daisy Duck')),
                                                                                          ('Bola', 5.00, 10.00, (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'), (SELECT categoria_id FROM tb_categoria WHERE descricao = 'Brinquedos'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Daisy Duck'));

INSERT INTO tb_pedido (data_pedido, forma_de_pagamento, status, usuario_id, pessoa_id) VALUES
                                                                                                   ( '2024-06-07 10:00:00', 'Cartão de Crédito', 'PENDENTE', (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'mickey'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Minnie Mouse')),
                                                                                                   ( '2024-06-07 10:05:00', 'PayPal', 'PENDENTE', (SELECT usuario_id FROM tb_usuario WHERE login_usuario = 'donald'), (SELECT pessoa_id FROM tb_pessoa WHERE nome = 'Daisy Duck'));

-- Inserção para Tabela PedidoItem
INSERT INTO tb_pedido_item ( quantidade, pedido_id, produto_id) VALUES
                                                                                   ( 1, 1, 1),
                                                                                   ( 2, 2, 2);
