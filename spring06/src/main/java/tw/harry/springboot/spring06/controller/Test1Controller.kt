package tw.harry.springboot.spring06.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/test")
class Test1Controller {

    @RequestMapping("/test1")
    fun test1(): String {
        return "test1"
    }

}