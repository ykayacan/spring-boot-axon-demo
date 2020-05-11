# Getting Started

This is an example Spring Boot project with Axon Framework to demonstrate CQRS event sourcing.

Required Java Version: 14 

## Usage

Run Axon Server

```bash
docker run -d -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

Run command service (default port: 8080)

```bash
./gradlew bootRun
```

Run query service (default port: 8081)

```bash
./gradlew bootRun
```

## Example

Send request to command service

```bash
curl -X POST http://localhost:8080/ship-order
```

Send request to query service

```bash
curl http://localhost:8081/all-orders
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
