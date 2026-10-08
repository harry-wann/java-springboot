package tw.harry.springboot.spring06.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/")
class MemberController {

    @GetMapping
    fun root(): String {
        return "redirect:/main"
    }

    @GetMapping("/login")
    fun login(
        @RequestParam(required = false, value = "error")
        error: String?,
        @RequestParam(required = false, value = "logout")
        logout: String?,
        model: Model
    ): String {
        if (!error.isNullOrBlank()) {
            model.addAttribute("error", "Login failed!")
        }
        if (!logout.isNullOrBlank()) {
            model.addAttribute("logout", "Logout successfully!")
        }
        return "login"
    }

    @GetMapping("/main")
    fun main(model: Model): String {
        model.addAttribute("companyName", "Big Harry Company")
        return "main"
    }

    @GetMapping("/members/page1")
    fun page1(): String {
        return "/members/page1"
    }

    @GetMapping("/admin")
    fun admin(): String {
        return "admin"
    }

    @GetMapping("/admin/page1")
    fun adminPage1(): String {
        return "/admin/admin1"
    }

    @GetMapping("/page403")
    fun page403(): String {
        return "page403"
    }
}