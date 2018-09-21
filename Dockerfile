FROM gradle:4.10.1-jdk10
RUN ["mkdir", "/home/gradle/src"]
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ["gradle", "wrapper", "--gradle-version", "4.4"]
RUN ["./gradlew", "clean", "run"]
CMD "/bin/bash"
