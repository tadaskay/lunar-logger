# Lunar homework

TODO describe

## Running app

```
./gradlew build
./gradlew bootRun
```

After startup, the following are accessible:

- API: http://localhost:8080
- Health: http://localhost:8080/health
- Metrics: http://localhost:8080/metrics

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
