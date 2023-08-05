INSERT INTO kitchen (name) VALUES ('Tailandesa');
INSERT INTO kitchen(name) VALUES ('Indiana');

INSERT INTO restaurant (name, shipping_fee, kitchen_id) VALUES ('Thai Gourmet', 10, 1);
INSERT INTO restaurant (name, shipping_fee, kitchen_id) VALUES ('Thai Delivery', 9.50, 1);
INSERT INTO restaurant (name, shipping_fee, kitchen_id) VALUES ('Tuk Tuk Comida Indiana', 15, 2);

INSERT INTO state (name) VALUES ('Minas Gerais');
INSERT INTO state (name) VALUES ('São Paulo');
INSERT INTO state (name) VALUES ('Ceará');

INSERT INTO city (name, state_id) VALUES ('Uberlândia', 1);
INSERT INTO city (name, state_id) VALUES ('Belo Horizonte', 1);
INSERT INTO city (name, state_id) VALUES ('São Paulo', 2);
INSERT INTO city (name, state_id) VALUES ('Campinas', 2);
INSERT INTO city (name, state_id) VALUES ('Fortaleza', 3);

INSERT INTO payment_method (description) VALUES ('Cartão de crédito');
INSERT INTO payment_method (description) VALUES ('Cartão de débito');
INSERT INTO payment_method (description) VALUES ('Dinheiro');

INSERT INTO permission (name, description) VALUES ('EDITAR_COZINHAS', 'Permite editar cozinhas');
INSERT INTO permission (name, description) VALUES ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
