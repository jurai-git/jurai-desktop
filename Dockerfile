FROM debian:11

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven wget binutils nano sudo fuse modprobe && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN wget -c "https://github.com/AppImage/AppImageKit/releases/download/continuous/appimagetool-x86_64.AppImage" -O /usr/local/bin/appimagetool && \
    chmod a+x /usr/local/bin/appimagetool

RUN apt-get install -y modprobe

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

WORKDIR /app

COPY . .

CMD ["true"]
