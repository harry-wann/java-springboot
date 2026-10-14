package tw.harry.springboot.springai.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class ProductFilter(
    @Size(max = 100)
    val keyword: String? = null,
    @Min(1) @Max(8)
    val categoryId: Int? = null,
    val maxPrice: BigDecimal? = null,
    val maxPriceInclusive: Boolean? = null,
    val isStockOnly: Boolean? = null, // 是否只查有庫存的商品
    @NotNull @Min(1) @Max(10)
    val limit: Int = 1,
)
