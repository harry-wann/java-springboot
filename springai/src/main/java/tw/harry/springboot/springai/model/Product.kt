package tw.harry.springboot.springai.model

import java.math.BigDecimal

data class Product(
    val productId: Int? = null,
    var productName: String? = null,
    var categoryId: Int? = null,
    var categoryName: String? = null,
    var quantityPerUnit: String? = null,
    var unitPrice: BigDecimal? = null,
    var unitsInStock: Int? = null,
    var discontinued: Boolean? = null,
)
