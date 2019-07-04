FROM node:10-alpine
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
ENV MQTT_HOST=dhmqtt
ENV MQTT_PORT=1883
ENV DB_HOST=172.31.224.33
RUN apk add --no-cache tzdata
ENV TZ America/Chicago
# CMD npm start
CMD [ "node", "./bin/www" ]
