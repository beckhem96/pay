package com.fastcampus.membership;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
//    private static final Logger log = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorLocation = "Unknown";
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            StackTraceElement element = stackTrace[0];
            // 예: "com.fc.membership.controller.MembershipController:42"
            errorLocation = element.getClassName() + ":" + element.getLineNumber();
        }

        boolean isDbError = e.getCause() instanceof SQLException; // DB 에러 판별 로직

        // MDC에 데이터를 넣으면 LogstashEncoder가 알아서 JSON 필드로 만들어줍니다!
        MDC.put("level", "ERROR");
        MDC.put("path", request.getRequestURI());
        MDC.put("status", String.valueOf(httpStatus.value()));
        MDC.put("error_location", errorLocation);
        MDC.put("error_type", e.getClass().getSimpleName());
        MDC.put("is_db_error", String.valueOf(isDbError));

        log.error("서버 내부 에러 발생: {}", e.getMessage(), e);

        MDC.clear(); // 🌟스레드 풀 환경에선 반드시 지워줘야 합니다!

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
}