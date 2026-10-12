package tw.harry.springboot.spring08.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class AITextService(
    builder: ChatClient.Builder,
) {

    private val chatClient: ChatClient = builder.build()

    fun describeProduct(text: String): String {
        return generate("""
            你是產品助理，請根據商品資料給出 10 ~ 100 字的描述
            按照真實資料給予回應，如果資訊不足則明確說明，切勿憑空捏造
            使用台灣繁體中文回覆
        """.trimIndent(), text)
    }

    fun complain(text: String): String {
        return generate("""
            你是客服專員，目前有客戶在抱怨
            請針對客訴內容回覆客戶疑慮，要很真誠的道歉且說明，10 ~ 100 字的描述
            \"\"不可承諾內部資訊如庫存、退款、寄送時間等你無法決定或者是重要的內部資訊\"\"
        """.trimIndent(), text)
    }

    fun generate(prompt: String, text: String): String {
        try {
            val result = chatClient
                .prompt()
                .system(prompt)
                .user(text)
                .call()
                .content()

            if (result.isNullOrEmpty()) {
                throw IllegalStateException("Model Response ERROR")
            }

            return result
        } catch(e: RuntimeException) {
            throw
        }
    }
}