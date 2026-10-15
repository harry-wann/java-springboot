package tw.harry.springboot.springai_db.tool

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import tw.harry.springboot.springai_db.dto.response.TableResult
import tw.harry.springboot.springai_db.dto.response.ToolTrace
import tw.harry.springboot.springai_db.service.DirectoryService
import tw.harry.springboot.springai_db.service.OrderQueryService


class NorthwindTool(
    private val directoryService: DirectoryService,
    private val orderQueryService: OrderQueryService,
    private val traces: MutableList<ToolTrace>
) {

    @Tool(description = "依英文姓名片段查全部符合的員工編號、姓名、職稱與主管編號；本工具只查基本資料。")
    fun searchEmployees(
        @ToolParam(description = "英文姓名片段, 查全部的話為 null", required = false)
        keyword: String?
    ): TableResult {
        return trace(
            "searchEmployees",
            "keyword",
            keyword,
            directoryService.employees(keyword)
        )
    }

    @Tool(description = "依公司英文名稱片段查全部符合的客戶代碼、公司與國家；代碼保留原大小寫及空白。")
    fun searchCustomers(
        @ToolParam(description = "公司英文姓名片段, 查全部的話為 null", required = false)
        keyword: String?
    ): TableResult {
        return trace(
            "searchCustomers",
            "keyword",
            keyword,
            directoryService.customers(keyword)
        )
    }

    @Tool(
        description = """
        依員工編號查全部訂單，依訂單編號遞減；
        適用使用者已提供員工編號；只有姓名時改用 searchOrdersByEmployeeName。
        """
    )
    fun searchOrdersByEmployee(
        @ToolParam(description = "員工編號", required = false)
        employeeId: Int?
    ): TableResult {
        return trace(
            "searchOrdersByEmployees",
            "employeeId", employeeId,
            orderQueryService.searchByEmployee(employeeId)
        )
    }

    @Tool(
        description = """
        依客戶代碼查全部訂單，依訂單編號遞減；
        適用使用者已提供客戶代碼；只有公司名時改用 searchOrdersByCustomerName
        """
    )
    fun searchOrdersByCustomer(
        @ToolParam(description = "客戶代碼", required = false)
        customerId: String?
    ): TableResult {
        return trace(
            "searchOrdersByCustomer",
            "customerId", customerId,
            orderQueryService.searchByCustomer(customerId)
        )
    }

    @Tool(
        description = """
        依員工姓名片段一次取得員工及全部訂單。
        查姓名的訂單優先用此工具。
        多人符合時只回傳候選名單，必須請使用者指定，不可自行選擇或繼續查候選訂單。
        """
    )
    fun searchOrdersByEmployeeName(
        @ToolParam(description = "員工姓名片段", required = false)
        keyword: String?
    ): TableResult {
        return trace(
            "searchOrdersByEmployeeName",
            "keyword", keyword,
            orderQueryService.searchByEmployeeName(keyword)
        )
    }

    @Tool(
        description = """
        依公司名稱片段一次取得客戶及全部訂單。
        查公司名的訂單優先用此工具。
        多家公司符合時只回傳候選名單，必須請使用者指定，不可自行選擇或繼續查候選訂單。
        """
    )
    fun searchOrdersByCustomerName(
        @ToolParam(description = "公司名稱片段", required = false) keyword: String?
    ): TableResult {
        return trace(
            "searchOrdersByCustomerName",
            "keyword", keyword,
            orderQueryService.searchByCustomerName(keyword)
        )
    }

    private fun trace(
        tool: String,
        parameter: String?,
        value: Any?,
        result: TableResult
    ): TableResult {
        val arguments = LinkedHashMap<String?, Any?>()
        arguments[parameter] = value
        traces.add(ToolTrace(tool, arguments, result))
        return result
    }
}