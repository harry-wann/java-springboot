package tw.harry.springboot.spring01.dto

data class Member(
    var id: Int? = null,
    var name: String? = null,
    var account: String? = null,
    var passwd: String? = null
)