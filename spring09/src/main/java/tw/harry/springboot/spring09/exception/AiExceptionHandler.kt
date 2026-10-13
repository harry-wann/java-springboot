package tw.harry.springboot.spring09.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AiExceptionHandler {

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        HttpMessageNotReadableException::class,
    )
    fun invalidInput(exception: Exception): ProblemDetail {
        return problem(
            status = HttpStatus.BAD_REQUEST,
            code = "INVALID_INPUT",
            detail = "輸入內容無效",
            providerStatus = null
        )
    }

    @ExceptionHandler(
        ModelOutputException::class,
    )
    fun invalidOutput(): ProblemDetail {
        return problem(
            status = HttpStatus.BAD_GATEWAY,
            code = "AI_OUTPUT_INPUT",
            detail = "Model 輸出格式有問題，請重新描述",
            providerStatus = null
        )
    }

    private fun problem(
        status: HttpStatus,
        code: String,
        detail: String,
        providerStatus: Int?
    ): ProblemDetail {
        val problem = ProblemDetail.forStatusAndDetail(
            status,
            detail,
        )
        problem.properties?.set("code", code)
        providerStatus?.let {
            problem.properties?.set("providerStatus", it)
        }
        return problem
    }

}