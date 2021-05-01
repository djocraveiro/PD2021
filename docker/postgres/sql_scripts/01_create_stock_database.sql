--CREATE DB ===================================================================
DROP DATABASE IF EXISTS stock_db;
DROP role IF EXISTS pguser;

BEGIN;
CREATE USER pguser WITH encrypted password 'pgpass';

COMMIT;

CREATE DATABASE stock_db
    WITH 
    OWNER = pguser
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c stock_db;
GRANT USAGE ON SCHEMA PUBLIC TO PUBLIC;
GRANT CREATE ON SCHEMA PUBLIC TO PUBLIC;
COMMENT ON DATABASE stock_db IS 'Virtual store stock api database';
GRANT ALL PRIVILEGES ON DATABASE stock_db TO pguser;


--CREATE DB TABLES ============================================================
BEGIN;

DROP TABLE IF EXISTS public.product;

CREATE TABLE public.product (
	id uuid NOT NULL,
	name VARCHAR(50) COLLATE pg_catalog."default",
	description VARCHAR(500) NOT NULL,
	available INT NOT NULL,
	price REAL NOT NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id)
);

COMMIT;