# planets-api

**Simple REST API using Javalin Microservices framework**

This project is just a test / POC.

Resources

* POST http://localhost:7000/planets (adds a new planet)
* GET http://localhost:7000/planets (list all planets)
* GET http://localhost:7000/planets/query/:name (returns the planet for the given ":name")
* GET http://localhost:7000/planets/:id (returns the planet for the given ":id")
* DELETE http://localhost:7000/planets/:id (deletes the planet for the given ":id")

### Some examples:

Adding a planet...

```
POST http://localhost:7000/planets

{ "name":"Tatooine", "climate":"arid", "terrain":"desert"}

Response: 201 - created
{ "id": 2737641910836278898, "name":"Tatooine", "climate": "arid", "terrain": "desert", "films": 5}
```
Trying to get a planet who is not registered (unknow id)

GET http://localhost:7000/planets/234

Response: 409 - conflict
{"error": 1002, "message": "Planet not found!" }

### Technology Stack

This project was built using Intellij IDEA Communit Edition 2018.3, Gradle 5.2 and Groovy, Java 1.8 (OpenSDK) and Mongo DB 4.1 (docker container).
If you intend to run the Integration Tests you should have docker installed. Take a look bellow in the "Testing" section.

### Application desing

The application was thought using concepts from DDD(Domain Driven Design), Clean Architecture, SOLID and general OOP good practices. Bellow, a list of its layers and a short description:

* domain: business logic
* application: business trasactions logic, domain coordenation. Anti-corruption layer (does not expose the domain)
* data mappers: abstraction to the persistence layer, persistence services
* controllers: http request handlers. Consumes the application services

### Testing

There are two test modules: "test", for "unit tests" and "testIntegration" for integration tests. The application design provides, in a very simple but effective way, a nice level of abstraction and low coupling between layers. Infrastructure dependencies like database access and external remote service calls are totally mock, so **Unit tests** can run fast, smoothly and concentrate on its main purpose: to test domain and application business logic. 

**Integration tests** are not totally automated so if you want to run it successfully, pay attention to the following steps:

1) Start the MongoDB docker container using the above command (perhaps you have to execut it as 'sudo' on Linux')

docker run --rm --name mongo-planets-api -p 27017:27017 -d mongo:4.1

2) From a terminal, start the application server:

/.../planets-api/source$ ./gradlew run

You should see something like this:
```
> Task :run
[main] INFO org.eclipse.jetty.util.log - Logging initialized @422ms to org.eclipse.jetty.util.log.Slf4jLog
[main] INFO io.javalin.Javalin - 
 _________________________________________
|        _                  _ _           |
|       | | __ ___   ____ _| (_)_ __      |
|    _  | |/ _` \ \ / / _` | | | '_ \     |
|   | |_| | (_| |\ V / (_| | | | | | |    |
|    \___/ \__,_| \_/ \__,_|_|_|_| |_|    |
|_________________________________________|
|                                         |
|    https://javalin.io/documentation     |
|_________________________________________|
[main] INFO io.javalin.Javalin - Starting Javalin ...
[main] INFO org.eclipse.jetty.server.Server - jetty-9.4.15.v20190215; built: 2019-02-15T16:53:49.381Z; git: eb70b240169fcf1abbd86af36482d1c49826fa0b; jvm 11.0.2+9
[main] INFO org.eclipse.jetty.server.session - DefaultSessionIdManager workerName=node0
[main] INFO org.eclipse.jetty.server.session - No SessionScavenger set, using defaults
[main] INFO org.eclipse.jetty.server.session - node0 Scavenging every 600000ms
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started i.j.c.u.@4cc451f2{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.s.ServletContextHandler@9d5509a{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@690fb24c{HTTP/1.1,[http/1.1]}{0.0.0.0:7000}
[main] INFO org.eclipse.jetty.server.Server - Started @729ms
[main] INFO io.javalin.Javalin - Jetty is listening on: [http://localhost:7000/]
[main] INFO io.javalin.Javalin - Javalin has started \o/
```
3) From the Intellij IDE, righ-click on the "testIntegration" module and click "Run all Tests"

4) After running the tests, stop the MondoDB container: docker stop mongo-planets-api

The container is automatically removed after stoping.

Comments, bugs reports, suggestions, etc. are welcome.
