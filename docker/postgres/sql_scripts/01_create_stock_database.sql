DROP DATABASE if exists dbpeer;
drop role if exists pguser;

BEGIN;
CREATE USER pguser WITH encrypted password 'pgpass';

COMMIT;

CREATE DATABASE dbpeer
    WITH 
    OWNER = pguser
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c dbpeer;
grant usage on schema public to public;
grant create on schema public to public;
 
COMMENT ON DATABASE dbpeer IS 'Base de Dados para API Peer Review';

GRANT ALL PRIVILEGES ON DATABASE dbpeer TO pguser;