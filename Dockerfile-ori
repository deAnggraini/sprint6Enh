FROM nginx:stable
COPY docker-files/includes /etc/nginx/includes/
COPY docker-files/dev-nginx.conf /etc/nginx/conf.d/default.conf
COPY /dist/dev /usr/share/nginx/html