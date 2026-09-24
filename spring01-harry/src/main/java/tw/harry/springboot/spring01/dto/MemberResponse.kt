package tw.harry.springboot.spring01.dto

import lombok.Data
import org.springframework.stereotype.Component

/*
* {
*   "error": 0,    0: OK; not 0: error
*   "msg": "",
*   "member": member
* }
* */
@Component
data class MemberResponse(
    var error: Int? = null,
    var msg: String? = null,
    var member: Member? = null,
    var members: List<Member>? = null
)
