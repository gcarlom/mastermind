Mastermind
=================

A Web application using
- Spring MVC
- Gradle to build project and generate the WAR file

### Developer Instructions

#### Set up the development environment
- Download/ clone the repository
- Import the project in your IDE
- Make changes
- Deploy on a web application server



#### Running / Testing the application locally

Using Gradle 2.6
```
gradle jettyRun
```
Deploys the application on localhost, start the server (jetty)

   or

```
gradle jettyRunWar
```
Same as above but also creates the WAR file

Open your browser at the URL
http://localhost:8080/mastermind/


**NB** Tasks jettyRun and jettyRunWar are deprecated in Gradle 3.1


#### Building, Testing and Generating the WAR file

To Generate the WAR file
```
gradle war
```
The WAR will be created into the build/libs directory
