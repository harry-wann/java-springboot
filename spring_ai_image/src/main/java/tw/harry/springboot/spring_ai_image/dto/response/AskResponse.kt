package tw.harry.springboot.spring_ai_image.dto.response

data class AskResponse(
    val decision: Decision,
    val reason: String,
) {
    enum class Decision {
        ACCEPT, REJECT, UNCERTAIN
    }
}
