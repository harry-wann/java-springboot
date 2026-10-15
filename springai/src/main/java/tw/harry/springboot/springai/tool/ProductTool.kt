package tw.harry.springboot.springai.tool

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import tw.harry.springboot.springai.dto.request.ProductFilter
import tw.harry.springboot.springai.dto.response.ProductLookup
import tw.harry.springboot.springai.service.ProductService
import java.math.BigDecimal


class ProductTool(
    private val service: ProductService,
) {

    @Tool(
        description = """
        查詢商品. 名稱是英文字串, 依照編號排序.
        類別: 1飲料 2調味料 3甜點 4乳製品 5穀類 6肉類 7農產品 8海鮮 
        """
    )
    fun searchProduct(
        @ToolParam(description = "英文名稱片段, 沒有的話就是 null", required = false)
        keyword: String?,

        @ToolParam(description = "類別 ID 1~8, 沒有的話就是 null", required = false)
        categoryId: Int?,

        @ToolParam(description = "非負數的上限值, 有值得話, maxPriceInclusive true, 沒有的話, 則為 null")
        maxPrice: BigDecimal?,

        @ToolParam(
            description = """
            與 maxPrice 一起看: 低於/小於為 false; 不超過/以下為 true.
            例如: 低於 20 為 maxPrice=20, maxPriceInclusive=false.
            有價格上限時, 不可為 null;
            只有梅價格上限的時候, 才可以 null
            """, required = false
        )
        maxPriceInclusive: Boolean?,  // 否只查有庫存

        @ToolParam(description = "指查有庫存的為 true, 未指定為 null", required = false)
        isStockOnly: Boolean?,  // 是

        @ToolParam(description = "回傳筆數 1~10, 未指定為 5")
        limit: Int
    ): ProductLookup {
        val filter = ProductFilter(
            keyword,
            categoryId,
            maxPrice,
            maxPriceInclusive,
            isStockOnly,
            limit
        )
        return service.search(filter)
    }

    @Tool(description = "依照商品編號查詢出商品的價格, 庫存等等; 找不到為 null")
    fun getProductById(
        @ToolParam(description = "商品編號, 必須大於 0") productId: Int
    ): ProductLookup {
        return service.findById(productId)
    }

}