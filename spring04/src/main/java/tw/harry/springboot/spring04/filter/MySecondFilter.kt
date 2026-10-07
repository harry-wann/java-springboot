package tw.harry.springboot.spring04.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

//@Order(1)
//@Component
class MySecondFilter : Filter {
    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        System.out.println("MySecond filter before: ${response?.outputStream?.println()}")
        chain?.doFilter(request, response)
        System.out.println("MySecond filter after: ${response?.outputStream?.println()}")
    }
}