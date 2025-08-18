FROM node:latest
LABEL org.opencontainers.image.authors="Paul CROS"

ARG MS_GATEWAY_USER
ARG MS_GATEWAY_PASSWORD

# Copie les sources de l'application
WORKDIR /opt/app-source
COPY . .

# Génre le fichier .env avec les variables d'environnement
RUN chmod +x /opt/app-source/docker-generate-env.sh
RUN /opt/app-source/docker-generate-env.sh

# Installe les dépendances et compile l'application
RUN npm install
# Build the application
RUN npm run build

# Copie les fichiers compilés dans le répertoire de l'application
# et supprime les sources pour alléger l'image
WORKDIR /opt/app
RUN cp -r /opt/app-source/dist/* ./ \
    && rm -rf /opt/app-source

# Expose le port 9080 et démarre le serveur HTTP
RUN npm install -g http-server
EXPOSE 9080
CMD ["http-server", "-p", "9080"]