package tw.harry.springboot.springai.repository

import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.json.JsonMapper
import tw.harry.springboot.springai.dto.request.ProductFilter
import tw.harry.springboot.springai.dto.response.ProductLookup
import tw.harry.springboot.springai.model.Product
import java.math.BigDecimal
import java.util.Optional

@Repository
class InMemoryProductRepository : ProductRepository {

    private val products: List<Product>

    init {
        val input = ClassPathResource("data/products.json").inputStream
        products = JsonMapper.builder()
            .build()
            .readValue(input, object : TypeReference<List<Product>>() {})
    }

    override fun search(filter: ProductFilter): ProductLookup {
        val keyword = if (filter.keyword.isNullOrEmpty()) {
            ""
        } else {
            filter.keyword.trim()
        }

        val matches = products
            .filter { it.productName?.contains(keyword, true) == true }
            .filter { (filter.categoryId == null) || (filter.categoryId == it.categoryId) }
            .filter { isOverPrice(it, filter)}
            .filter { filter.isStockOnly == false || it.unitsInStock!! > 0 }

        val page = matches.take(filter.limit)

        return ProductLookup(
            products = page,
            totalMatches = matches.size,
            truncated = (matches.size > page.size)
        )
    }

    /*
        特定商品的價格，是否符合設定的價格上限
        true: 符合
        false: 不符
     */
    private fun isOverPrice(product: Product, filter: ProductFilter): Boolean {
        if (filter.maxPrice == null || filter.maxPrice <= BigDecimal.ZERO) {
            return true
        }
        if (product.unitPrice == null || product.unitPrice!! <= BigDecimal.ZERO) {
            return true
        }

        val compare = product.unitPrice!!.compareTo(filter.maxPrice)
        /*
            負數: product.unitPrice < filter.maxPrice
            0: product.unitPrice = filter.maxPrice
            正 : product unirPrice > filter.maxPrice
         */
        if (filter.maxPriceInclusive == true) {
            return compare <= 0
        } else {
            return compare < 0
        }
    }

    override fun findById(productId: Int): Optional<Product> {
        return Optional.ofNullable(products.firstOrNull { it.productId == productId })
    }
}
