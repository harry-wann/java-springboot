package tw.harry.springboot.spring01.dto

import org.springframework.web.multipart.MultipartFile

data class MemberForm(
    val account: String = "",
    val files: List<MultipartFile> = listOf(),
)