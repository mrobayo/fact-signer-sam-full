insert into si_grupo_cliente (activo, name, id) values (true, 'Particular', 'PARTICULAR');
insert into si_grupo_cliente (activo, name, id) values (true, 'IESS', 'IESS');
insert into si_grupo_cliente (activo, name, id) values (true, 'Referido', 'REFERIDO');

insert into si_unidad (base, factor, name, tipo, id) values (true, 1, 'unidad', 'EMPAQUE', 'UN');

insert into si_usuario (email, identidad, name, nunca_expira, password, roles, telefono, id) values ('factsigner@gmail.com', '3141592653', 'FactSigner Admin By Mario Robayo', true, '$2a$10$lWtTx6Uk9VaehdH/f.tB9OjaovsCkCeOyAY0YQEb9FOqeBBxl635K', 'ROLE_ADMIN', '0999948420', 'ADMIN');

insert into si_empresa (comercial, direccion, name, tarifa_iva, telefono, id) values ('DEMO', 'Padre Hurtado y Alejo Lascano', 'DEMO', 12, '0967800045', '1100000000001');

insert into si_categoria (empresa_id, name, id) values ('1100000000001', 'BASICO', 1);

insert into si_producto (categoria_id, codigo, codigo_iva, empresa_id, name, unidad_venta_id, vendido, id) values (1, '101', 2, '1100000000001', 'LAPIZ', 'UN', true, gen_random_uuid());

