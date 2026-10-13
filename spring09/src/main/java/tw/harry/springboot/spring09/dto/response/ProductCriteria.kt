package tw.harry.springboot.spring09.dto.response

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import tw.harry.springboot.spring09.enums.Category
import java.math.BigDecimal

data class ProductCriteria(
    @field:Size(max = 100)
    val keyword: String? = null,
    val category: Category? = null,
    @field:DecimalMin("0")
    val maxPrice: BigDecimal? = null,
    val maxPriceInclusive: Boolean? = null,
    @field:Pattern(regexp = "[A-Z]{3}")
    val currency: String? = null,
    val memo: List<String> = emptyList(),
)
