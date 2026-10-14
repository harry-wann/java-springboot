package tw.harry.springboot.springai.service

import jakarta.validation.Validator
import org.springframework.stereotype.Service
import tw.harry.springboot.springai.dto.request.ProductFilter
import tw.harry.springboot.springai.dto.response.ProductLookup
import tw.harry.springboot.springai.repository.ProductRepository
import kotlin.streams.toList

/*
    不透過 AI
 */
@Service
class ProductService(
    private val repo: ProductRepository
) {

    fun search(filter: ProductFilter): ProductLookup {
        return repo.search(filter)
    }

    fun findById(productId: Int): ProductLookup {
        val products = repo.findById(productId).stream().toList()
        return ProductLookup(
            products = products,
            totalMatches = products.size,
            truncated = false,
        )
    }
}