FROM openjdk:8u121-jre

ENV APP_HOME /opt/lunarcrawler

WORKDIR $APP_HOME
ADD build/libs/crawler*.jar app.jar

EXPOSE 9010

HEALTHCHECK --interval=10s --timeout=3s --retries=20 \
  CMD curl -f http://localhost:9010/health || exit 1

ENTRYPOINT exec java $JAVA_OPTS -server \
  -Dfile.encoding=UTF-8 \
  -jar app.jar
