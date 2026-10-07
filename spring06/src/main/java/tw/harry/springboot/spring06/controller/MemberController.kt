package tw.harry.springboot.spring06.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class MemberController {

    @GetMapping
    fun root(): String {
        return "redirect:/main"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}