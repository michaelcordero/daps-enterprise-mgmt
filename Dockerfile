# ./gradlew installDist
# docker build -t my-application .
# docker run -it --name "karen" -dp 8080:8080 daps
FROM openjdk:8-jdk
EXPOSE 8080
RUN mkdir /app
COPY /build/install/daps-enterprise-mgmt/ /app/
COPY /db.mv.db /app/
WORKDIR /app/
CMD ["./bin/daps-enterprise-mgmt"]

