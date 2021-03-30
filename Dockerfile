FROM openjdk:8-jdk
RUN mkdir /app
COPY /build/install/daps-enterprise-mgmt/ /app/
WORKDIR /app/bin
CMD ["./daps-enterprise-mgmt"]

