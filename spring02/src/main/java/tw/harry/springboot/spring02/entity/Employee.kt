package tw.harry.springboot.spring02.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "employees")
class Employee(
    @Id
    var employeeId: String? = null,
    var lastName: String? = null,
    var firstName: String? = null,
    var title: String? = null,

    // ------------------
    @OneToMany(mappedBy = "employee")
    var orders: MutableList<Order> = mutableListOf()
)
