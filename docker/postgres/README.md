# Requirements
Require docker and docker-compose already installed.


# How to setup
docker-compose up -d


# Database Explorer
1. Open browser at "http://localhost:15432/"
2. Insert credentials from docker-compose.yml file (PGADMIN_DEFAULT_EMAIL and PGADMIN_DEFAULT_PASSWORD)
3. Click login
4. On tree view right click on "server" node  then "create" > "server".
5. Fill General.name field (ex: postgres)
6. Switch to Connection tab, then fill host with "postgres_vstore" and leave default port (5432).
7. Insert credentials from  docker-compose.yml file (POSTGRES_USER and POSTGRES_PASSWORD)
8. Click save and explore database.