package tw.harry.springboot.springai_db.dto.response

data class ToolTrace(
    val tool: String,
    val arguments: Any,
    val result: TableResult
)