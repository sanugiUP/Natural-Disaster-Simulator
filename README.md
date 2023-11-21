# Project Description 
## Overview
This repository hosts a comprehensive Java application that simulates various natural disasters to evaluate and test emergency service responses. Leveraging key design patterns like dependency injection, observer pattern, and state pattern, this simulator provides a platform to assess emergency services' preparedness and effectiveness.

## Features
### Disaster Simulation
> Natural Disaster Scenarios: Simulates a range of disasters such as earthquakes, floods, wildfires, etc., with varying magnitudes and impact zones.

> Real-time Simulation: Offers a real-time simulation environment to test emergency service responses dynamically.

### Design Patterns Integration
> Dependency Injection: Implements dependency injection principles for efficient and flexible management of dependencies.

> Observer Pattern: Utilizes the observer pattern to notify emergency services about changes in disaster conditions.

> State Pattern: Implements the state pattern to model different states of the disasters and emergency service responses.

### Emergency Service Evaluation
> Response Assessment: Monitors and evaluates emergency services' responses to simulated disasters.

> Performance Analysis: Measures response time, effectiveness, and coordination between services during disaster scenarios.

## Usage
> On the command line, use ./gradlew run --args fileName where fileName represents the name of the input file.

> Alternatively, type ./gradlew run and input the file name when prompted.

## Documentation
> UML Diagrams: Offers comprehensive UML Class and State Diagrams in the "Diagrams" folder, detailing class relationships and state transitions within the application.

> Response to Criteria: Includes a text file ("criteria.txt") addressing specific project criteria and requirements.

## Testing Input
The provided "input.txt" file serves as a test input for simulating natural disasters and evaluating emergency service responses. Modifications in the ResponderCommImpl class enable testing with the data entries included in this file.

## Purpose
The Natural Disaster Simulator aims to create a controlled environment for testing emergency service responses to various natural calamities. It serves as a tool to analyze and enhance emergency service strategies, resource allocation, and disaster management protocols.
