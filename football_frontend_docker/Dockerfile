# Build stage
FROM node:latest as build-stage
WORKDIR /app

# Configure npm to use mirror
RUN npm config set registry https://registry.npmmirror.com/

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"] 