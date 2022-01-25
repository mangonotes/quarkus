FROM azul/zulu-openjdk-alpine:17-jre-headless


EXPOSE 6100


ARG JAR_FILE=target/todo-1.0.0-SNAPSHOT-runner.jar
ADD ${JAR_FILE} todo.jar
# Run the jar file
ENTRYPOINT ["java", "--enable-preview", "-Duser.timezone=Asia/Singapore", "-jar", "/todo.jar"]
