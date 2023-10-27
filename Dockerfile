FROM openjdk
VOLUME /tmp
ADD target/${artifactId}-0.0.1.jar ${artifactId}-0.0.1.jar
EXPOSE 8080 
ENTRYPOINT ["java","-jar","${artifactId}-0.0.1.jar"]