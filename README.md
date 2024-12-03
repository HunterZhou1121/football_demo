# football_demo
A demo project with Vue.js frontend, Spring Boot backend and MySQL, containerised with Docker. 

## Project Structure
+ football_frontend_docker: Vue.js frontend, containerised with Docker
+ football_backend_docker: Spring Boot backend, containerised with Docker
+ docker-compose.yaml: Docker Compose file to run the project, where MySQL is introduced as a service

## Project Breakdown

We break down the project into three parts: Database, Backend and Frontend. Each part is containerised with Docker as a service, and they are linked together using Docker Compose. 

### Database
> To use MySQL in a standalone container, use the following command: 
```bash
docker run --name mysql -p 3307:3306 -v mysql_volume:/var/lib/mysql/ -d -e "MYSQL_ROOT_PASSWORD=temp123" mysql
```

In this project, we introduce MySQL from the official Docker Hub image. 
```yaml
mysql_database:
image: mysql:latest
container_name: mysql
environment:
    MYSQL_ROOT_PASSWORD: temp123
ports:
    - "3307:3306"
volumes:
    - mysql_volume:/var/lib/mysql
    - ./mysql/load_data.sql:/docker-entrypoint-initdb.d/load_data.sql
networks:
    - spring-mysql
```
By using "3307:3306", we map the port 3307 of the host machine to the port 3306 of the container, since the port 3306 of the host machine may be occupied by other services like a local MySQL server. To access the MySQL server from the host machine using JDBC, use the following URL: `jdbc:mysql://127.0.0.1:3307/football`. Note that using `localhost` instead of `127.0.0.1` may encounter network issues. If you want to test the connection from the host machine using `mysql` command, remember to shut down the MySQL service on the host machine, or you may end up connecting with it instead of the one in the container. 

Here, a Docker persistent volume named `mysql_volume` is created to persist the MySQL data, as when the container is stopped, the data will be lost as well. To initialise the database with `.sql` files, move them into the `/docker-entrypoint-initdb.d` directory of the container, so that when the container is started, the data will be loaded automatically. 

### Backend
```yaml
football_backend:
build: ./football_backend_docker
ports:
    - "8080:8080"
depends_on:
    - mysql_database
networks:
    - spring-mysql
```
> The backend uses Amazon Corretto 21 as the JDK and Maven as the build tool, with the Maven mirror configured to the Aliyun Maven Mirror. See the `football_backend_docker/Dockerfile` for more details.

The backend is a Spring Boot application containerised with Docker. The connections are configured in the `football_backend_docker/src/main/resources/application.properties` file. Since the backend and the MySQL database are both in the same Docker network `spring-mysql`, the backend can access the MySQL database directly using the container name `mysql` and the port `3306`.

After the backend is started, you can use `curl http://localhost:8080/players` on the host machine to test the connection. However, for security reasons, the frontend will not be able to access the backend directly. The Cross-Origin Resource Sharing (CORS) is configured in the `football_backend_docker/src/main/java/org/example/football_backend_docker/CorsConfig.java` file, where requests from the Vue.js default server port `5173` and the Docker frontend port `81` are allowed. 

### Frontend
```yaml
football_frontend:
build: ./football_frontend_docker
ports:
    - "81:80"
depends_on:
    - football_backend
networks:
    - spring-mysql
```
> The frontend uses the latest stable version of Node.js and npm, and the latest stable version of nginx. The npm mirror is configured to the Taobao npm mirror. See the `football_frontend_docker/Dockerfile` for more details. 

Here we use the port `81` on the host machine as the default port `80` is occupied. Since the JavaScript code is running directly in the browser, the backend needs to allow requests from the port `81` instead of the port `80` that is assigned to the frontend container. 

## How to run the project
1. Run `docker compose up -d` in the root directory to start the project
2. Open `http://localhost:81` in your browser to view the frontend
3. Use `docker compose down` to stop the project