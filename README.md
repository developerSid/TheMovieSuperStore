#The Movie Super Store
An demo project for Utilizing different parts of the Spring Framework via the opinated 
Spring Boot project.

## Getting started
The project uses Gradle for it's builds to simplify dependency (library) management 
as well as quick and easy JAR creation for running the application.

## Modules

### Data
This where the JPA Entities are defined and configured. To help make iterating on the ideas
expressed in this project these data entities are reused in multiple ways throughout the project
 
### Loader
This is the primary application as it stands right now.  It pulls in data from https://developers.themoviedb.org/3/getting-started
You can setup a developer's account and run the loader application using the API key they
generate for you.

To Run:
./gradlew clean build
java -jar Loader/build/libs/Loader.jar --spring.profiles.active=embedded --tmdb.api.key=${yourApiKeyGoesHere}
