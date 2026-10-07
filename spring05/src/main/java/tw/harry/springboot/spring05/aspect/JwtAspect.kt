package tw.harry.springboot.spring05.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import tw.harry.springboot.spring05.exception.JwtAuthException
import tw.harry.springboot.spring05.util.JwtToken

@Aspect
@Component
class JwtAspect {

    @Around("@annotation(tw.harry.springboot.spring05.annotation.CheckJwt)")
    fun checkJwt(point: ProceedingJoinPoint): Any? {
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request: HttpServletRequest = attributes.request
        request.session
        val urIp = request.remoteAddr
        println("IP: $urIp")
        val authHeader = request.getHeader("Authorization") ?: throw JwtAuthException("Token is empty.")

        if (!authHeader.startsWith("Bearer")) {
            throw JwtAuthException("Token format is not correct.")
        }

        try {
            val token = authHeader.split(" ").getOrNull(1) ?: ""
            val data = JwtToken.parseToken(token)
            print(data)
        } catch (e: Exception) {
            e.printStackTrace()
            throw JwtAuthException("Token is invalid.")
        }

        return point.proceed()
    }
}