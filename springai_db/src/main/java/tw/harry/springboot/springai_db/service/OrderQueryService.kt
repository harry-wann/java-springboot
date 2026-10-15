package tw.harry.springboot.springai_db.service

import org.springframework.stereotype.Service
import tw.harry.springboot.springai_db.dto.response.TableResult
import tw.harry.springboot.springai_db.entity.Order
import tw.harry.springboot.springai_db.repository.CustomerRepository
import tw.harry.springboot.springai_db.repository.EmployeeRepository
import tw.harry.springboot.springai_db.repository.OrderRepository

@Service
class OrderQueryService(
    private val orderRepo: OrderRepository,
    private val employeeRepo: EmployeeRepository,
    private val customerRepo: CustomerRepository,
) {

    /**
     * 依員工編號查詢訂單。
     */
    fun searchByEmployee(employeeId: Int?): TableResult {
        val results = orderRepo.findByEmployee(employeeId)
        return toTable(
            results,
            "員工編號 $employeeId"
        )
    }

    /**
     * 依客戶代碼查詢訂單。
     * 保留原始大小寫與尾端空白。
     */
    fun searchByCustomer(customerId: String?): TableResult {
        val results = orderRepo.findByCustomer(customerId)
        return toTable(
            results,
            "客戶代碼 $customerId"
        )
    }

    /**
     * 依員工姓名查詢。
     *
     * 找不到 → 回傳提示
     * 多人符合 → 回傳候選員工，不自行選擇
     * 唯一符合 → 回傳該員工的全部訂單
     */
    fun searchByEmployeeName(keyword: String?): TableResult {

        val matches = employeeRepo.searchWithOrders(
            DirectoryService.toPattern(keyword)
        )

        if (matches.isEmpty()) {
            return TableResult(
                column = listOf("員工編號", "姓名"),
                row = emptyList(),
                note = "找不到符合姓名的員工。"
            )
        }

        if (matches.size > 1) {
            val candidates = matches.map { employee ->
                listOf(
                    employee.employeeId,
                    "${employee.firstName} ${employee.lastName}"
                )
            }
            return TableResult(
                column = listOf("員工編號", "姓名"),
                row = candidates,
                note = "需要澄清：符合多位員工。這是候選名單，不是訂單；請使用者指定員工編號，不可自行選擇。"
            )
        }

        val employee = matches.first()

        return toTable(
            employee.orders,
            "員工 ${employee.firstName} ${employee.lastName}（編號 ${employee.employeeId}）"
        )
    }

    /**
     * 依公司名稱查詢。
     *
     * 找不到 → 回傳找不到
     * 多家公司符合 → 回傳候選公司
     * 唯一符合 → 回傳該公司的全部訂單
     * 沒有訂單 → 仍保留該公司，只是結果為空
     */
    fun searchByCustomerName(keyword: String?): TableResult {

        val matches = customerRepo.searchWithOrders(
            DirectoryService.toPattern(keyword)
        )

        if (matches.isEmpty()) {
            return TableResult(
                column = listOf("客戶代碼", "公司名稱"),
                row = emptyList(),
                note = "找不到符合名稱的客戶。"
            )
        }

        if (matches.size > 1) {

            val candidates = matches.map { customer ->
                listOf(
                    customer.customerId,
                    customer.companyName
                )
            }

            return TableResult(
                column = listOf("客戶代碼", "公司名稱"),
                row = candidates,
                note = "需要澄清：符合多家公司。這是候選名單，不是訂單；請使用者指定客戶代碼，不可自行選擇。"
            )
        }

        val customer = matches.first()

        return toTable(
            customer.orders,
            "客戶 ${customer.companyName}（代碼 ${customer.customerId}）"
        )
    }

    /**
     * 統一將訂單轉成 TableResult。
     */
    private fun toTable(
        results: Collection<Order>,
        condition: String?
    ): TableResult {

        val rows = results
            .sortedByDescending { it.orderId }
            .map { order ->
                listOf(
                    order.orderId,
                    order.customerId,
                    order.employeeId,
                    order.orderDate?.toString(),
                    order.shippedDate?.toString(),
                    order.freight
                )
            }

        return TableResult(
            column = listOf(
                "訂單編號",
                "客戶代碼",
                "員工編號",
                "訂購日期",
                "出貨日期",
                "運費"
            ),
            row = rows,
            note = "$condition；依訂單編號遞減，回傳此條件的全部訂單。結果為空表示此主體沒有符合訂單；運費無幣別。"
        )
    }
}