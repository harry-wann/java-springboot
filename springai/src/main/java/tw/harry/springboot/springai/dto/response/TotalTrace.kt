package tw.harry.springboot.springai.dto.response

data class TotalTrace(
    val tool: String? = null,
    val args: Any? = null,
    val result: ProductLookup? = null,
)
