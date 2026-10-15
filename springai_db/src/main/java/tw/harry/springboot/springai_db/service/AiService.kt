package tw.harry.springboot.springai_db.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service
import tw.harry.springboot.springai_db.dto.response.AskResponse
import tw.harry.springboot.springai_db.dto.response.ToolTrace
import tw.harry.springboot.springai_db.tool.NorthwindTool

@Service
class AiService(
    private val chatClient: ChatClient,
    private val directoryService: DirectoryService,
    private val orderQueryService: OrderQueryService
) {
    fun ask(text: String): AskResponse {
        val traces = mutableListOf<ToolTrace>()
        val tool = NorthwindTool(
            directoryService,
            orderQueryService,
            traces
        )

        val response = chatClient.prompt()
            .system("""
                你是 Northwind 員工、客戶、訂單查詢助理，以繁體中文回答。
                所有資料事實必須依本次工具，不猜測資料庫內容。
                使用者已提供員工 ID／客戶代碼才使用 ID 工具；只有名稱則用名稱訂單工具，不需先查目錄。
                客戶代碼保留大小寫與尾端空白。
                查無結果就明說；工具列出本次條件的全部符合資料，不等於資料庫所有資料。每次為獨立請求，沒有跨輪記憶。
                不要拿你之前所認識的 Northwind 資料庫來做預設處理, 忘掉他.
            """)
            .user { user ->
                user.text("查詢什麼: {input}")
                    .param("input", text)
            }
            .tools(tool)
            .call()
            .chatResponse()

        val answer = response?.result?.output?.text
        return AskResponse(
            answer = answer,
            dataSource = "MySQL",
            toolCalls = traces
        )
    }
}