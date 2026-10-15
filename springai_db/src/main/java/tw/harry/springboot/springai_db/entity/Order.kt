package tw.harry.springboot.springai_db.entity

import jakarta.persistence.*
import lombok.Data
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "Orders")
class Order(
    @Id
    @Column(name = "OrderID")
    var orderId: Int? = null,

    @Column(name = "CustomerID")
    var customerId: String? = null,

    @Column(name = "EmployeeID")
    var employeeId: String? = null,

    @Column(name = "OrderDate")
    var orderDate: String? = null,

    @Column(name = "RequiredDate")
    var requiredDate: LocalDateTime? = null,

    @Column(name = "ShippedDate")
    var shippedDate: LocalDateTime? = null,

    @Column(name = "Freight", precision = 10, scale = 4)
    var freight: BigDecimal? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", insertable = false, updatable = false)
    var customer: Customer? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID", insertable = false, updatable = false)
    var employee: Employee? = null,
)

