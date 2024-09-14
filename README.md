# quarkus-spring-weather-app

# Weather API and Client

This repository contains two projects:

1. **quarkus-weather**: A REST API built with Quarkus that provides weather information for a given location.
2. **weather-client**: A web client built with Spring Boot that consumes the Quarkus Weather API.

These projects are an evolution of the original console application [weatherylocation](https://github.com/gazolla/weatherylocation), updated with knowledge gained from the DIO.me course on containers.

## Technologies Used

### Quarkus Weather API
- Quarkus
- RESTEasy
- Jackson
- Java

### Weather Client
- Spring Boot
- Thymeleaf
- htmx
- Tailwind CSS
- Java

## Project Structure

```
/
├── quarkus-weather/
│   └── [Quarkus project files]
├── weather-client/
│   └── [Spring Boot project files]
└── README.md
```

## Setup and Running

### Configure API Keys:

To use this application, you will need to include the API key generated on the weatherstack website here in the config.properties file.
You will also need to include the API key generated on the mapbox website here in the config.properties file.

Then add your keys to application.properties file on quarkus-weather project
	```
	weather.api.key=YOUR_WEATHERSTACK_API_KEY
	geolocation.api.key=YOUR_MAPBOX_API_KEY
	```


### Quarkus Weather API

1. Navigate to the `quarkus-weather` directory.
2. Build the project:
   ```
   ./mvnw clean package
   ```
3. Run the application:
   ```
   java -jar target/quarkus-app/quarkus-run.jar
   ```
   The API will be available at `http://localhost:8080`.

### Weather Client

1. Navigate to the `weather-client` directory.
2. Build the project:
   ```
   ./mvnw clean package
   ```
3. Run the application:
   ```
   java -jar target/weather-client-0.0.1-SNAPSHOT.jar
   ```
   The client will be available at `http://localhost:8081`.

## Usage

1. Start both the Quarkus Weather API and the Spring Boot Weather Client.
2. Open a web browser and navigate to `http://localhost:8081`.
3. Enter a location in the text field and submit to see the weather information.

## API Endpoints

- `GET /weather/{location}`: Get weather information for a specific location.

## Acknowledgements

- Original project: [weatherylocation](https://github.com/gazolla/weatherylocation)
- Course on containers: [DIO.me](https://dio.me/)

## License

[MIT License](LICENSE)