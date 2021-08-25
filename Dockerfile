### STAGE 1: Build ###
FROM node:lts-alpine AS build

#### make the 'app' folder the current working directory
WORKDIR /usr/src/app

#### copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./

#### install angular cli
RUN npm install -g @angular/cli

#### install project dependencies
RUN npm install

#### copy things
COPY . .

#### generate build --prod
RUN npm run build-dev


FROM nginxinc/nginx-unprivileged

COPY docker-files/includes /etc/nginx/includes/
COPY docker-files/dev-nginx.conf /etc/nginx/conf.d/default.conf
COPY docker-files/nginx.conf /etc/nginx/nginx.conf

#### copy artifact build from the 'build environment'
COPY --from=build /usr/src/app/dist/dev /usr/share/nginx/html\

#### don't know what this is, but seems cool and techy
#CMD ["nginx", "-g", "daemon off;"]
