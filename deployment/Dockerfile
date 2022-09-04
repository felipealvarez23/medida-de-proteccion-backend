FROM adoptopenjdk/openjdk11-openj9:alpine-slim
VOLUME /tmp
ENV JAVA_OPTS=" -Dspring.profiles.active=pnd -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
ARG JAR_FILE=applications/app-service/build/libs/medida_de_proteccion_backend.jar
ADD ${JAR_FILE} medida_de_proteccion_backend.jar
EXPOSE 80
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar medida_de_proteccion_backend.jar" ]
