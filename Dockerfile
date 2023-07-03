FROM ${builderImage:-ubuntu:focal} AS builder

ENV JAVA_HOME=/opt/java/openjdk
COPY --from=ibm-semeru-runtimes:open-17-jdk $JAVA_HOME $JAVA_HOME
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /opt/app
COPY . .
RUN ./mvnw clean package -Dmaven.test.skip=true


FROM ubuntu:focal

ENV JAVA_HOME=/opt/java/openjdk
COPY --from=ibm-semeru-runtimes:open-17-jre $JAVA_HOME $JAVA_HOME
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /opt/app

COPY --from=builder /opt/app/target/app.jar /opt/app/app.jar

RUN echo timestamp = $(date +%FT%T%:z) >> /opt/app/version.info

ENV JAVA_OPTS="-Xmx2048m"
ENV TZ Europe/Rome

ENTRYPOINT java ${JAVA_OPTS} -XshowSettings:vm -jar app.jar
