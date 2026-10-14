package tw.harry.springboot.springai.dto.response

import tw.harry.springboot.springai.model.Product

data class ProductLookup(
    val products: List<Product>,
    val totalMatches: Int,
    val truncated: Boolean,
)
