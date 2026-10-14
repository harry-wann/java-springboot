package tw.harry.springboot.springai.repository

import org.springframework.context.annotation.Bean
import tw.harry.springboot.springai.dto.request.ProductFilter
import tw.harry.springboot.springai.dto.response.AskResponse
import tw.harry.springboot.springai.dto.response.ProductLookup
import tw.harry.springboot.springai.model.Product
import java.util.Optional

interface ProductRepository {

    fun search(filter: ProductFilter): ProductLookup
    fun findById(productId: Int): Optional<Product>

}