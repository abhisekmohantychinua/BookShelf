FROM gradle:8-jdk21-alpine as build
WORKDIR /build
COPY . .
RUN gradle clean build -x test

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /build/build/libs/api-0.0.1-*.jar .
ENV CORS=http://localhost:4200
ENV DB_URL=jdbc:mysql://mysql:3306/book_shelf
ENV DB_USERNAME=root
ENV DB_PASSWORD=root
EXPOSE 8080
CMD ["java", "-jar","api-0.0.1-SNAPSHOT.jar","--spring.datasource.url=${DB_URL}","--spring.datasource.username=${DB_USERNAME}","--spring.datasource.password=${root}","--env.cors=${CORS}"]