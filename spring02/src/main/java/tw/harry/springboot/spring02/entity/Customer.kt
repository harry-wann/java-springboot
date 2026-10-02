package tw.harry.springboot.spring02.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "customers")
class Customer(
    @Id
    @Column(name = "CustomerID")
    var customerId: String? = null,

    @Column(name = "CompanyName")
    var companyName: String? = null,

    @Column(name = "ContactName")
    var contactName: String? = null,

    // ------------------
    @OneToMany(mappedBy = "customer")
    var orders: MutableList<Order> = mutableListOf()
)
