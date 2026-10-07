package tw.harry.springboot.spring04.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class HarryAspect {

    @Around("@annotation(tw.harry.springboot.spring04.annotation.HarryAop)")
    fun around(point: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()

        val methodName = point.signature.name
        val args = point.args
        println("${methodName}: ${args.count()}: ${args.joinToString(", ")}")

        args[1] = args[1].toString().uppercase()

        println("around111()")
        val obj = point.proceed(args)
        println("around222()")
        System.out.println("${System.currentTimeMillis() - start}ms")
        return obj
    }
}