### assemble angular in docker
FROM quay.io/d3wiaccred/nginx-unprivileged

WORKDIR /pakar

COPY docker-files/includes /etc/nginx/includes/
COPY docker-files/dev-nginx.conf /etc/nginx/conf.d/default.conf
COPY docker-files/nginx.conf /etc/nginx/nginx.conf

#### copy artifact build from the 'build environment'
COPY dist/dev .

EXPOSE 8080:8080

#### don't know what this is, but seems cool and techy
#CMD ["nginx", "-g", "daemon off;"]
