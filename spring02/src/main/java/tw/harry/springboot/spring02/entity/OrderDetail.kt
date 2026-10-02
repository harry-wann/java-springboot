package tw.harry.springboot.spring02.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "orderdetails")
@IdClass(OrderDetailPK::class)
data class OrderDetail(
    @Id
    @Column(name = "orderID")
    var orderId: Int? = null,
    @Id
    @Column(name = "ProductID")
    var productId: Int? = null,

    var unitPrice: BigDecimal? = null,
    var quantity: Int? = null,

    // -----------
    @ManyToOne
    @JoinColumn(name = "OrderID")
    @JsonBackReference
    var order: Order? = null,

    @ManyToOne
    @JoinColumn(name = "ProductID")
    var product: Product? = null,
)
