package tw.harry.springboot.springai.service

import com.openai.models.ChatModel
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service
import tw.harry.springboot.springai.dto.response.AskResponse
import tw.harry.springboot.springai.tool.ProductTool

@Service
class ProductAiService(
    builder: ChatClient.Builder,
    private val productService: ProductService,
) {
    private val chatClient: ChatClient = builder.build()

    fun ask(text: String): AskResponse {
        val tool = ProductTool(productService)
        val response = chatClient.prompt()
            .system {
                """
                    你是資深商品管理員，你可以針對條件快速做商品查詢
                """.trimIndent()
            }
            .user { user ->
                user.text("")
                    .param("input", text)
            }
            .tools(tool)
            .call()
            .chatResponse()

        val answer = response?.result?.output?.text

        return AskResponse(
            answer = answer,
            datasource = "JSON"
        )
    }

}