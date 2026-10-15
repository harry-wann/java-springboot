package tw.harry.springboot.springai_db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "Customers")
class Customer(
    @Id
    @Column(name = "CustomerID")
    var customerId: String? = null,

    @Column(name = "CompanyName")
    var companyName: String? = null,

    @Column(name = "Country")
    var country: String? = null,

    @Column(name = "City")
    var city: String? = null,

    // ------------------
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    var orders: MutableList<Order> = mutableListOf()
)