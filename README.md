# Lunar logger

For a given URL, **crawler** is extracting famous people and storing them in remote repository.

**logger** REST API provides write-ahead-log functionality for the **crawler**. 

## Running app

Pre-requisites:

- JDK8
- docker 1.12.0+
- docker-compose 1.6.0+
- for IDE: lombok plugin and enabled annotation-processors

```
$ ./gradlew build
$ docker-compose up --build
```

After startup, the following are accessible:

- Logger API docs: http://localhost:8080/swagger-ui.html
- Logger API: http://localhost:8080/api
- Logger health: http://localhost:8080/health
- Logger metrics: http://localhost:8080/metrics

Add more crawler instances:

```
$ docker-compose scale crawler=5
```

## Design notes

- To avoid handcrafting requests in tests, `logger-api` has been extracted,
which also serves as a client-side SDK (for Java based implementation of crawlers). 
In the real world, this module should be versioned separately and tests executed against
specific versions independently (similar to Consumer Driven Contracts)
- Emphasis on package design with clear dependencies and no cycles:

```
url -> celebrities
url -> remotekey
registrar -> *
```

## Acknowledgments

To minimize the scope, the following were not implemented:

- Hypermedia
- Paging
- Security
- Query optimizations
- Proper immutability on representations, etc.
- Document locking/versioning
- Metrics exporter to console (they are available via endpoint)
- Additional layer of separation between entities and resources, 
but given the size of the app, it is absolutely not needed at the moment
