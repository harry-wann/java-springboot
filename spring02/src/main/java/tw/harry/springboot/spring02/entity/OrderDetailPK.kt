package tw.harry.springboot.spring02.entity

import java.io.Serializable
import java.util.Objects

data class OrderDetailPK(
    val orderId: Int = 0,
    val productId: Int = 0,
): Serializable {

    override fun hashCode(): Int {
        return Objects.hash(orderId, productId)
    }

    override fun equals(other: Any?): Boolean {
        return (this === other) ||
                (other is OrderDetailPK)
                && (orderId == other.orderId)
                && (productId == other.productId)
    }
}
