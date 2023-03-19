#
# Build stage
#
ARG JAVA_VERSION=11.0.7_10
FROM adoptopenjdk/openjdk11:jdk-${JAVA_VERSION}-alpine as compiler
COPY . .
RUN apk add sed \
  && ./gradlew shadowJar --no-daemon
#
# Run stage
#
FROM adoptopenjdk/openjdk11:jre-${JAVA_VERSION}-alpine
ARG ENV
ENV JVM_ARGS -XX:+UseContainerSupport \
  -XX:InitialRAMPercentage=60 \
  -XX:MaxRAMPercentage=60 \
  -XX:MaxDirectMemorySize=200m \
  --add-opens=java.base/java.net=ALL-UNNAMED

ENV APP_JAR_FILE service.jar
ENV APP_HOME /usr/app

COPY --from=compiler /build/libs/. $APP_HOME/
WORKDIR $APP_HOME

ENTRYPOINT ["sh", "-c"]
CMD ["exec java ${JVM_ARGS} -Denv=${ENV} -jar ${APP_JAR_FILE}"]
EXPOSE 8080
