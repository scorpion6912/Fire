# Dockerfile for creating an vue3 app with vite and volume persitent
 
# Set the base image
FROM openjdk:21-oracle
 
# Maintainer
LABEL maintainer="Remy"

COPY target/fire-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "fire-0.0.1-SNAPSHOT.jar"]
EXPOSE 9080
 
# Set environment variables
# ENV APP_DIR=/back_app

# # Create app directory
# RUN mkdir -p $APP_DIR
 
# # Copy the source code
# COPY . $APP_DIR
# WORKDIR $APP_DIR

# RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
# RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar)

# VOLUME /tmp
# ARG DEPENDENCY=/workspace/app/build/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","fire.Application"]
