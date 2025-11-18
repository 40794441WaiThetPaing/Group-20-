# Use latest MySQL server
FROM mysql/mysql-server:latest

# Set working directory
WORKDIR /tmp

# Copy SQL files for initial data
COPY world-db/*.sql /tmp/
COPY world-db/*.dump /tmp/
COPY world-db/world.sql /docker-entrypoint-initdb.d/

# Set root password and create root@% for remote access
ENV MYSQL_ROOT_PASSWORD=example

# Add remote access grant script (executed on container startup)
COPY remote-access.sql /docker-entrypoint-initdb.d/