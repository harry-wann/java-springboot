package tw.harry.springboot.spring05.handler

import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice // 攔截器，攔截例外
class GlobalExceptionHandler {

    @ExceptionHandler(JwtException::class)
    fun handleJwtException(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(mapOf(
                "success" to false,
                "msg" to "權限被拒"
            ))
    }
}