# 1. 가볍고 안정적인 Java 17 알파인 리눅스 사용 (본인 자바 버전이 11이나 21이면 숫자만 바꾸세요!)
FROM eclipse-temurin:17-jre-alpine

# 2. 컨테이너 내부의 작업 폴더 설정
WORKDIR /app

# 3. 로컬에서 빌드된 jar 파일을 도커 컨테이너 안으로 복사
# (Gradle 빌드 기준입니다. Maven을 쓰신다면 target/*.jar 로 수정하세요)
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 4. K8s 안에서 컨테이너가 켜질 때 실행할 명령어 (스프링부트 런!)
ENTRYPOINT ["java", "-jar", "app.jar"]