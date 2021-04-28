DROP DATABASE if exists stock_db;
drop role if exists pguser;

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
grant usage on schema public to public;
grant create on schema public to public;
 
COMMENT ON DATABASE stock_db IS 'Virtual store stock api database';

GRANT ALL PRIVILEGES ON DATABASE stock_db TO pguser;