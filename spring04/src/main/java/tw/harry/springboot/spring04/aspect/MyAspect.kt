package tw.harry.springboot.spring04.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import java.util.Date

@Aspect
@Component
class MyAspect {

    init {
        System.out.println("MyAspect init")
    }

    @Pointcut("execution(* tw.harry.springboot.spring04.controller.MyController.*(..))")
    fun doMyController() {

    }

    @Before("doMyController()")
    fun doBefore() {
        System.out.println("MyAspect before")
    }

    @After("doMyController()")
    fun doAfter() {
        System.out.println("MyAspect after")
    }

    @Around("doMyController()")
    @Throws(Throwable::class)
    fun doAround(point: ProceedingJoinPoint): Any {
        val now = System.currentTimeMillis()
        System.out.println("MyAspect around 1: ${now}")
        val obj = point.proceed()
        System.out.println("MyAspect around 2: ${obj}")
        System.out.println("MyAspect around 2: ${System.currentTimeMillis() - now}")
        return obj
    }
}