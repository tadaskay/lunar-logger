FROM openjdk:8u121-jre

RUN useradd -ms /bin/bash app
RUN mkdir -p /home/app/.embedmongo
RUN chown app:app /home/app/.embedmongo

USER app
WORKDIR /home/app
ADD build/libs/logger*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=3s --retries=20 \
  CMD curl -f http://localhost:8080/health || exit 1

ENTRYPOINT exec java $JAVA_OPTS -server \
  -Dfile.encoding=UTF-8 \
  -jar app.jar
