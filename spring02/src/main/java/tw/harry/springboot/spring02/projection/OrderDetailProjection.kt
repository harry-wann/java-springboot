package tw.harry.springboot.spring02.projection


import org.springframework.beans.factory.annotation.Value
import java.math.BigDecimal

interface OrderDetailProjection {
    fun getUnitPrice(): BigDecimal
    fun getQuantity(): Int

    // SpEL
    @Value("#{target.product.productName}")
    fun getProductName(): String

    @Value("#{target.unitPrice * target.quantity}")
    fun getSubTotal(): BigDecimal
}