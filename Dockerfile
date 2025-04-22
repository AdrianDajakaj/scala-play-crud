FROM openjdk:21-slim

RUN apt-get update && \
    apt-get install -y curl unzip && \
    rm -rf /var/lib/apt/lists/*

ENV SBT_VERSION=1.9.6
RUN curl -L -o sbt.tgz https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz && \
    tar -xvzf sbt.tgz && \
    mv sbt /usr/local/sbt && \
    ln -s /usr/local/sbt/bin/sbt /usr/bin/sbt

WORKDIR /app

COPY . .

RUN sbt compile

CMD ["sbt", "run"]
