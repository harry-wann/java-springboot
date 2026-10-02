package tw.harry.springboot.spring03.controller

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import tw.harry.springboot.spring03.apis.MemberForm
import tw.harry.springboot.spring03.apis.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
@RequestMapping("/")
class WebController {

    /*
        prefix + viewName + suffix
        prefix: classpath:/templates/
        suffix: .html
     */
    @RequestMapping("/index")
    fun index(): String {
        return "index"
    }

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

        val now = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
        model.addAttribute("now", now)

        return "/page1"
    }

    @RequestMapping("/page2/{status}")
    fun page2(model: Model, @PathVariable status: String): String {
        model.addAttribute("status", status)
        return "/page2"
    }

    @GetMapping("/page3")
    fun page3(model: Model): String {
        val form = MemberForm()
//        form.account = "輸入帳號"
        model.addAttribute("form", form)
        return "/page3"
    }

    @PostMapping("/page3")
    fun afterPage3(
        model: Model,
        @ModelAttribute("form")
        @Valid
        form: MemberForm,
        req: BindingResult,
    ): String {

        System.out.println(form.account)
        System.out.println(form.password)
        System.out.println(form.name)

        if (req.hasErrors()) {
            return "/page3"
        }

        return "/page4"
    }

    @RequestMapping("/page5")
    fun page5(model: Model): String {
        val areas = listOf(
            "北屯區",
            "南屯區",
            "西屯區",
            "西區",
            "北區"
        )
        model.addAttribute("areas", areas)
        return "/page5"
    }
}