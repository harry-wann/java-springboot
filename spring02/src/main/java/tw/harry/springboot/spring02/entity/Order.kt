package tw.harry.springboot.spring02.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @Column(name = "OrderID")
    var orderId: Int? = null,
    var orderDate: Date? = null,

    // ------------------
    @ManyToOne
    @JoinColumn(name = "CustomerID")
    @JsonBackReference
    var customer: Customer? = null,

    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    @JsonBackReference
    var employee: Employee? = null,

    @OneToMany(mappedBy = "order")
    var orderDetails: MutableList<OrderDetail>? = null
)