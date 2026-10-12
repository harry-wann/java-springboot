package tw.harry.springboot.spring08.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class AIController {

    @GetMapping("/active")
    fun active(): Map<String, String> {
        return mapOf("status" to "up")
    }


}