FROM alpine/java:22

COPY ./build/libs/bandportal-all.jar .

ENTRYPOINT ["java", "-jar", "bandportal-all.jar"]