FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /tmp

COPY ./src ./src

RUN javac src/main/java/index/Main.java -cp src/main/java -d dst/classes \
    && jar --create --file index.jar --main-class index.Main -C ./dst/classes . -C src/main/resources .\
    && jlink --add-modules java.base \
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

CMD ["/opt/jre-21/bin/java", "-jar", "./index.jar"]