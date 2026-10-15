package tw.harry.springboot.springai_db.dto.response

data class TableResult(
    val column: List<String>,
    val row: List<List<*>>,
    val note: String
)