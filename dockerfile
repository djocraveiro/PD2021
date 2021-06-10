FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-debian

VOLUME /tmp

#add required dependencies if necessary

ADD stock/target/stock.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar