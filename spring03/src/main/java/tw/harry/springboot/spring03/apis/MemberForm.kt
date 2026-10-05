package tw.harry.springboot.spring03.apis

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

class MemberForm(
    var account: String? = null,
    var password: String? = null,
    var name: String? = null,
    var iconFile: MultipartFile? = null,
)