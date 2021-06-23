FROM node

WORKDIR /usr/src/pakar/app/dummy

COPY package*.json ./

RUN npm install
COPY . .

EXPOSE 8080
CMD [ "node", "./bin/www" ]