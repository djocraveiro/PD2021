--SWITCH DB ===================================================================
\c stock_db;


--CREATE DATA ============================================================
BEGIN;

--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--required extension to generate uuid with uuid_generate_v4()

INSERT INTO public.product (id, name, description, available, price)
	VALUES('430769e6-c6ed-4dd8-b52c-9bc997fcfa43', 'Bola', 'Bola de futebol 11', 100, 7.99);

INSERT INTO public.product (id, name, description, available, price)
	VALUES('430769e6-c6ed-4dd8-b52c-9bc997fcfa45', 'Copos de cristal', 'Conjunto de 6 copos de cristal', 32, 15.99);

INSERT INTO public.product (id, name, description, available, price)
	VALUES('430769e6-c6ed-4dd8-b52c-9bc997fcfa47', 'Canetas BIC', 'Caneta azul da BIC', 1000, 0.99);

COMMIT;