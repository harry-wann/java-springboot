package tw.harry.springboot.springai.tool

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import tw.harry.springboot.springai.dto.request.ProductFilter
import tw.harry.springboot.springai.dto.response.ProductLookup
import tw.harry.springboot.springai.service.ProductService
import java.math.BigDecimal

class ProductTool(
    private val service: ProductService,
) {

    @Tool(description =  """
        查詢商品，名稱是英文字串，依照編號排序
        類別: 1飲料 2調味料 3甜點 4日用品(起司) 5穀類 6肉類 7農產品 8海鮮
    """)
    fun searchProduct(
        @ToolParam(description = "英文名稱的片段，沒有的話就是 null", required = false)
        keyword: String?,
        @ToolParam(description = "類別的 ID 1~8，沒有的話就是 null", required = false)
        categoryId: Int?,
        @ToolParam(description = "非負數的上限值, 有值的話，maxPriceInclusive 必須為 true，沒有的話則為 null")
        maxPrice: BigDecimal?,
        @ToolParam(description = """
            與 maxPrice 一起看: <低於/小於> 為 false；<不超過/以下> 為 true
            例如[低於20，則 maxPrice=20, maxPriceInclusive=false]
            有價格上限時不可為 null；
            只有沒價格上限的時候，才可以 null
        """, required = false)
        maxPriceInclusive: Boolean?,
        @ToolParam(description = "只查有庫存的為 true，未指定的為 null")
        isStockOnly: Boolean?,
        @ToolParam(description = "回傳筆數 1-10, 未指定為 5")
        limit: Int = 1,
    ): ProductLookup {
        val filter = ProductFilter(
            keyword = keyword,
            categoryId = categoryId,
            maxPrice = maxPrice,
            maxPriceInclusive = maxPriceInclusive,
            isStockOnly = isStockOnly,
            limit = limit,
        )
        return service.search(filter)
    }

    @Tool(description =  "依照商品編號查詢出商品的價格、庫存等等；找不到的話為 null")
    fun getProductById(
        @ToolParam(description = "商品編號，必須大於0")
        productId: Int,
    ): ProductLookup {
        return service.findById(productId)
    }

}