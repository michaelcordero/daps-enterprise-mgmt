FROM openjdk:8-jdk
EXPOSE 8080
RUN mkdir /app
COPY /build/install/daps-enterprise-mgmt/ /app/
WORKDIR /app/
CMD ["./bin/daps-enterprise-mgmt"]

