# Lunar homework

TODO describe

## Running app

Pre-requisites:

- JDK8
- docker 1.12.0+
- docker-compose 1.6.0+

```
./gradlew build
docker-compose up --build
```

After startup, the following are accessible:

- Logger API docs: http://localhost:8080/swagger-ui.html
- Logger API: http://localhost:8080/api
- Logger health: http://localhost:8080/health
- Logger metrics: http://localhost:8080/metrics

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
