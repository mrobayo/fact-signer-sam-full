insert into si_grupo_cliente (activo, name, id) values (true, 'Particular', 'PARTICULAR');
insert into si_grupo_cliente (activo, name, id) values (true, 'IESS', 'IESS');
insert into si_grupo_cliente (activo, name, id) values (true, 'Referido', 'REFERIDO');

insert into si_unidad (base, factor, name, tipo, id) values (true, 1, 'unidad', 'EMPAQUE', 'UN');

insert into si_usuario (email, identidad, name, nunca_expira, password, roles, telefono, id, empresas) values ('factsigner@gmail.com', '3141592653', 'FactSigner Admin By Mario Robayo', true, '$2a$10$lWtTx6Uk9VaehdH/f.tB9OjaovsCkCeOyAY0YQEb9FOqeBBxl635K', 'ROLE_ADMIN', '0999948420', 'ADMIN', '1100000000001');

insert into si_empresa (comercial, direccion, name, tarifa_iva, telefono, color, id) values ('EMPRESA DEMO', 'Padre Hurtado y Alejo Lascano', 'EMPRESA DEMO S.A.', 12, '0967800045', '#f44336', '1100000000001');

insert into si_categoria (empresa_id, name, id) values ('1100000000001', 'BASICO', 1);

insert into si_producto (categoria_id, codigo, codigo_iva, empresa_id, name, unidad_venta_id, vendido, id) values (1, '101', 2, '1100000000001', 'LAPIZ', 'UN', true, gen_random_uuid());

insert into si_punto_venta (direccion, empresa_id, estab, pto_emi, telefono, id) values ('.', '1100000000001', '001', '001', '2150012', '1100000000001-001-001');

insert into si_cliente (
 apellidos, ciudad, direccion, email, grupo_id, identidad, name, nombres,
 pais, telefono, tipo, id) values ('Apellidos', 'Guayaquil', 'Direccion', 'factsigner@gmail.com', 'PARTICULAR',
 '0910203040', 'Nombre Apellidos', 'Nombres', 'EC', '2157638', 'CED', 'facade00-0000-4000-a000-000000000000');

insert into si_cliente (
 apellidos, ciudad, direccion, email, grupo_id, identidad, name, nombres,
 pais, telefono, tipo, id) values ('Apellidos', 'Guayaquil', 'Direccion', 'factsigner@gmail.com', 'PARTICULAR',
 '0510203040', 'Nombre Apellidos', 'Nombres', 'EC', '2157638', 'CED', 'decade00-0000-4000-a000-000000000000');
