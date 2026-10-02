package tw.harry.springboot.spring03.apis

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class MemberForm(
    @field:NotBlank(message = "Account 不可空")
    @field:Email(message = "Email 格式不正確")
    var account: String? = null,

    @field:Size(min = 6, message = "密碼長度 >= 6")
    var password: String? = null,

    @field:NotBlank(message = "Name 不可空")
    var name: String? = null,
)