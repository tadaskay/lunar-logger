FROM openjdk:8u121-jre

ENV APP_HOME /opt/lunarlogger

RUN mkdir -p $APP_HOME/.embedmongo
WORKDIR $APP_HOME
ADD build/libs/logger*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=3s --retries=20 \
  CMD curl -f http://localhost:8080/health || exit 1

ENTRYPOINT exec java $JAVA_OPTS -server \
  -Dfile.encoding=UTF-8 \
  -jar app.jar
