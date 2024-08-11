# Exercise 01 – Messaging

## Starting the server

In the SocketServer root folder run the following commands:

`mvn clean package`

`java -jar target/socket-server.jar --port=8081`

## Starting the client

In the ClientSocket root folder run the following commands:

`mvn clean package`

`java -jar target/socket-client.jar --server-port=8081`
