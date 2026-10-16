package tw.harry.springboot.spring_ai_image.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.util.MimeTypeUtils
import tw.harry.springboot.spring_ai_image.dto.request.AskRequest
import tw.harry.springboot.spring_ai_image.dto.response.AskResponse
import kotlin.io.encoding.Base64

@Service
class AiService(
    private val chatClient: ChatClient,
) {
    fun analyze(request: AskRequest): AskResponse? {
        val bytes = Base64.decode(request.base64)

        return chatClient.prompt()
            .system("""
                你是影像辨識高手，檢查影像是否符合條件，並分類如以下
                ACCEPT: 影像符合條件
                REJECT: 只能有一個人，不可以是動物，卡通，物品，風景，不雅，AI生成
                UNCERTAIN: 模糊，太暗或其他因素無法判斷
            """.trimIndent())
            .user { user ->
                user
                    .text("""
                        判斷這張影像資料是否符合"人像", "大頭貼"
                    """.trimIndent())
                    .media(
                        MimeTypeUtils.parseMimeType(request.type),
                        ByteArrayResource(bytes)
                    )
            }
            .call()
            .entity(AskResponse::class.java)
    }
}