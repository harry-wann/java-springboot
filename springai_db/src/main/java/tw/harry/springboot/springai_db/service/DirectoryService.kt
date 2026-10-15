package tw.harry.springboot.springai_db.service

import org.springframework.stereotype.Service
import tw.harry.springboot.springai_db.dto.response.TableResult
import tw.harry.springboot.springai_db.repository.CustomerRepository
import tw.harry.springboot.springai_db.repository.EmployeeRepository
import java.util.*

@Service
class DirectoryService(
    private val employeeRepo: EmployeeRepository,
    private val customerRepo: CustomerRepository
) {

    fun employees(keyword: String?): TableResult {
        val results = employeeRepo.search(toPattern(keyword))
        // Arrays.asList 允許 null，例如員工沒有主管時保留缺值。
        val rows = results.stream()
            .map({ employee ->
                listOf(
                    employee.employeeId,
                    employee.firstName + " " + employee.lastName,
                    employee.title,
                    employee.country,
                    employee.reportsTo
                )
            })
            .toList()
        return TableResult(
            listOf("員工編號", "姓名", "職稱", "國家", "主管編號"),
            rows,
            "符合姓名條件的全部員工；不含薪資與私人聯絡資料。"
        )
    }

    fun customers(keyword: String?): TableResult {
        val results = customerRepo.search(toPattern(keyword))
        val rows = results.stream()
            .map({ customer ->
                listOf(
                    customer.customerId,
                    customer.companyName,
                    customer.country,
                    customer.city
                )
            })
            .toList()
        return TableResult(
            listOf("客戶代碼", "公司名稱", "國家", "城市"),
            rows,
            "符合公司名稱條件的全部客戶；不含電話、地址與聯絡人。"
        )
    }

    companion object {
        /** 只轉換 LIKE 格式，不驗證資料；員工與客戶共用相同的關鍵字處理。  */
        fun toPattern(keyword: String?): String {
            if (keyword.isNullOrBlank()) {
                return "%"
            }
            // % 與 _ 保留 LIKE 萬用字元意義，實際值由 Repository 綁定。
            return "%" + keyword.trim { it <= ' ' }.lowercase(Locale.getDefault()) + "%"
        }
    }

}