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

To Run the basic embedded example:
1. ./gradlew clean build
1. java -jar Loader/build/libs/Loader.jar --spring.profiles.active=embedded --tmdb.api.key=${yourApiKeyGoesHere}

### Server
This module uses a Spring Data Rest to expose the repositories defined in the Data module through HATEOAS

To Run this server
1. ./gradlew clean build
1. java -jar Server/build/libs/Server.jar --spring.profiles.active=embedded
1. Open a browser
1. Navigate to http://localhost:8080
   1. The provided JSON markup shows the available enpoints at the root.
   1. You can better utilize this navigation method using [Postman](https://www.getpostman.com)
   
   
## Putting it all together

### Docker
There are three shell scripts that will run (*-start.sh) Docker containers each being a different relational database.  These
 containers are used to illustrate the simplicity of switching between relational database implementations without a code change.
 This is of course assuming that you haven't had to do anything database specific.
