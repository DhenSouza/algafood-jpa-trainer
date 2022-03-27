insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Douglas restaliv', 3.9, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Joao marmitas', 13.9, 2);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Joao tailandesa', 44.9, 1);

insert into formaPagamento (descricao) values ('Cartão Debito');
insert into formaPagamento (descricao) values ('Cartão Credito');
insert into formaPagamento (descricao) values ('A vista');

insert into permissao (nome, descricao) values ('Consultar Produtos', 'Status liberado para a consulta de produtos');
