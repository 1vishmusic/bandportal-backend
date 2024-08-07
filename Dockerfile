FROM alpine/java:22

COPY ./build/libs/bandportal-all.jar .

EXPOSE 80

ENTRYPOINT ["java", "-jar", "bandportal-all.jar"]