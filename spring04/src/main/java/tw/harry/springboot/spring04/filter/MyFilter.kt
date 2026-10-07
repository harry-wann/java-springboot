package tw.harry.springboot.spring04.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.stereotype.Component

//@Component
class MyFilter : Filter {
    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        System.out.println("Myfilter before: ${response == null}")
        response?.outputStream
        chain?.doFilter(request, response)
        System.out.println("Myfilter after: ${response == null}")
    }
}