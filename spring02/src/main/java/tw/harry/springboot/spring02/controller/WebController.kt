package tw.harry.springboot.spring02.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring02.apis.User

@Controller
class WebController {


    @RequestMapping("/member")
    fun memberIndex(): String {
        return "/member/index"
    }

    @RequestMapping("/page1")
    fun page1(model: Model): String {
        model.addAttribute("companyName", "Big Harry Company")
        model.addAttribute("userName", "Harry")

        val user = User()
        user.id = 1
        user.age = 18
        user.gender = false
        user.name = "Vivi"
        model.addAttribute("user", user)

        return "/page1"
    }

}