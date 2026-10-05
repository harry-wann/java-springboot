package tw.harry.springboot.spring03.controller

import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.hibernate.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import tw.harry.springboot.spring03.apis.MemberForm
import tw.harry.springboot.spring03.exception.MemberAccountExistsException
import tw.harry.springboot.spring03.service.MemberService

@Controller
@RequestMapping("/members")
class MemberController(
    val service: MemberService
) {

    @GetMapping("/register")
    fun register(model: Model): String {
        val form = MemberForm()
        model.addAttribute("form", form)
        return "register"
    }

    @PostMapping("/register")
    fun doRegister(
        model: Model,
        @ModelAttribute("form") @Valid form: MemberForm,
        result: BindingResult,
    ): String {
        if (result.hasErrors()) {
            return "register"
        }
        try {
            val member = service.register(form)
            return "redirect:/members/login"
        } catch (e: MemberAccountExistsException) {
            model.addAttribute("error", "Account EXIST!")
            return "register"
        } catch (e: Exception) {
            e.printStackTrace()
            model.addAttribute("error", e)
            return "register"
        }
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/login")
    fun doLogin(
        @RequestParam account: String,
        @RequestParam password: String,
        model: Model,
        session: HttpSession,
    ): String {
        val member = service.login(account, password)
        if (member != null) {
            session.setAttribute("member", member)
            return "redirect:/home"
        }
        return "redirect:/members/login"
    }

    @RequestMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/members/login"
    }
}