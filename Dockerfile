FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /tmp

COPY ./src ./src

RUN javac src/main/java/index/Main.java -cp src/main/java -d dst/classes \
    && jar --create --file index.jar -C ./dst/classes . -C src/main/resources .\
    && jlink --add-modules java.base,java.sql \
        --compress zip-1 \
        --no-header-files \
        --no-man-pages \
        --strip-debug \
        --ignore-signing-information \
        --output ./jre-21

FROM alpine:3.20.3

WORKDIR /usr/local/index

COPY --from=builder /tmp/index.jar ./
COPY --from=builder /tmp/jre-21 /opt/jre-21

RUN wget https://repo1.maven.org/maven2/org/hsqldb/hsqldb/2.7.3/hsqldb-2.7.3.jar -O ./hsqldb-2.7.3.jar

CMD ["/opt/jre-21/bin/java", "-cp", "./hsqldb-2.7.3.jar:./index.jar", "index.Main"]
