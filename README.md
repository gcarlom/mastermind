Mastermind
=================

A Web application using
- Spring MVC
- Gradle to build project and generate the WAR file

== Developer Instructions ==

=== Set up the development environment ===
- Download/ clone the repository
- import the project in your IDE
- Make your changes
- Deploy on a web application server

=== Building, Testing and Generating the WAR file ===

With Gradle v.2.6

Builds and generate the WAR file (under the build/lib directory)
```
gradle war
```

Runs the application locally

Build and deploy the application on local server (Jetty)
```
gradle jettyRun
```

or
```
gradle jettyRunWar
```
to also generate the War file

Open your browser at
http://gcarlom.com:9080/springmvcsample/home


NB in Gradle 3.1 jettyRun and jettyRunWar are deprecated

