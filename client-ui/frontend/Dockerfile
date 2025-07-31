FROM node:latest
LABEL authors="Paul CROS"

ADD . /app/
WORKDIR /app
RUN npm install

EXPOSE 9080
ENTRYPOINT ["npm", "run", "dev"]