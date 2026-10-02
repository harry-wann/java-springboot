package tw.harry.springboot.spring01.dto

data class Base64Upload(
    val fileName: String = "",
    val contentType: String = "",
    val base64: String = "",
)
