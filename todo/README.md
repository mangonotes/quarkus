# Todo Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode
Docker is pre-requisites to enable devservices.
You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

For uat database goto /todo/db/ 
```shell script
sudo docker-compose up
```
Running with uat profile
./mvnw clean quarkus:run -Dquarkus-profile=uat