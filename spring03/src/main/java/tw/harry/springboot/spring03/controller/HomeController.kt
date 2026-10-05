package tw.harry.springboot.spring03.controller

import jakarta.servlet.http.HttpSession
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import tw.harry.springboot.spring03.entity.Member
import tw.harry.springboot.spring03.repo.GiftRepo
import java.util.Base64

@Controller
@RequestMapping("/home")
class HomeController(
    val giftRepo: GiftRepo
) {

    @GetMapping
    fun home(
        session: HttpSession,
        model: Model,
        @RequestParam(defaultValue = "0")
        page: Int,
        @RequestParam(defaultValue = "10")
        pageSize: Int,
    ): String {
        val member = session.getAttribute("member") as? Member ?: return "redirect:/members/login"
        model.addAttribute("member", member)

        if (member.icon != null) {
            val iconStr = "data:image/*; base64, " + Base64.getEncoder().encodeToString(member.icon)
            model.addAttribute("icon", iconStr)
        } else {
            model.addAttribute("icon", "")
        }

        val pageable = PageRequest.of(page, pageSize)
        val gifts = giftRepo.findAll(pageable)
        model.addAttribute("gifts", gifts)



        return "/home"
    }
}