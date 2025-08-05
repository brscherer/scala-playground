# Sidecar Design Pattern - Proof of Concept

This project demonstrates the **Sidecar Design Pattern** implementation in Scala, showcasing how to augment a main service with additional capabilities without modifying its core logic.

## What is the Sidecar Design Pattern?

The Sidecar Design Pattern is a microservices architecture pattern where additional functionality is deployed alongside a main application in a separate process or container. The sidecar provides supporting features like logging, monitoring, configuration, networking, or security without requiring changes to the primary application code.

### Key Characteristics:

- **Separation of Concerns**: Cross-cutting concerns are handled by the sidecar
- **Language Agnostic**: The main service and sidecar can be written in different languages
- **Minimal Coupling**: The main service remains focused on business logic
- **Reusability**: Sidecars can be shared across multiple services

### Common Use Cases:

- **Logging and Monitoring**: Centralized logging, metrics collection, and observability
- **Service Mesh**: Network communication, load balancing, and service discovery
- **Security**: Authentication, authorization, and encryption
- **Configuration Management**: Dynamic configuration updates
- **Circuit Breaking**: Fault tolerance and resilience patterns

## Project Implementation

This proof of concept demonstrates a logging sidecar that intercepts and logs HTTP requests and responses from a main service.

### Architecture Overview

```
┌─────────────────┐    ┌──────────────────┐
│   MainService   │───▶│   SidecarLogger  │
│                 │    │                  │
│ - Business Logic│    │ - Request Logging│
│ - HTTP Calls    │    │ - Response Logging│
│                 │    │ - Error Handling │
└─────────────────┘    └──────────────────┘
```

### Code Structure

#### 1. Sidecar Interface (`Sidecar` trait)
```scala
trait Sidecar {
  def logRequest(serviceName: String, request: String): Unit
  def logResponse(serviceName: String, response: String): Unit
}
```
Defines the contract for sidecar functionality.

#### 2. Sidecar Implementation (`SidecarLogger`)
```scala
class SidecarLogger extends Sidecar {
  override def logRequest(serviceName: String, request: String): Unit =
    println(s"[Sidecar] [$serviceName] Request sent: $request")

  override def logResponse(serviceName: String, response: String): Unit =
    println(s"[Sidecar] [$serviceName] Response received: $response")
}
```
Concrete implementation that handles logging of requests and responses.

#### 3. Main Service (`MainService`)
```scala
class MainService(sidecar: Sidecar) {
  val serviceName = "MainService"

  def fetchData(url: String): Future[String] = {
    sidecar.logRequest(serviceName, url)
    
    val response = HttpClient.get(url)
    
    response.onComplete {
      case Success(body) => sidecar.logResponse(serviceName, body)
      case Failure(ex)   => println(s"[Sidecar] [$serviceName] ERROR: ${ex.getMessage}")
    }
    
    response
  }
}
```
The main business logic that delegates cross-cutting concerns to the sidecar.

#### 4. HTTP Client (`HttpClient`)
```scala
object HttpClient {
  def get(url: String): Future[String] = Future {
    Thread.sleep(Random.between(100, 500)) // Simulate latency
    s"Response from $url"
  }
}
```
Simulates HTTP requests with random latency.

## Running the Project

### Prerequisites
- Scala 3.3.6+
- SBT (Scala Build Tool)

### Build and Run
```bash
# Compile the project
sbt compile

# Run the main application
sbt run
```

### Output
```
[Sidecar] [MainService] Request sent: http://api.service-a
[Sidecar] [MainService] Request sent: http://api.service-b
[Sidecar] [MainService] Response received: Response from http://api.service-a
[Sidecar] [MainService] Response received: Response from http://api.service-b
=== All requests handled ===
```
