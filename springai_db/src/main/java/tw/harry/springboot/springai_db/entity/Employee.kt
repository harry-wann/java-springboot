package tw.harry.springboot.springai_db.entity

import jakarta.persistence.*

@Entity
@Table(name = "employees")
class Employee(
    @Id
    @Column(name = "EmployeeID")
    var employeeId: Int? = null,

    @Column(name = "FirstName")
    var firstName: String? = null,

    @Column(name = "LastName")
    var lastName: String? = null,

    @Column(name = "Title")
    var title: String? = null,

    @Column(name = "Country")
    var country: String? = null,

    @Column(name = "ReportsTo")
    var reportsTo: Int? = null,

    // ReportsTo 是員工自我關聯；不輸出生日、電話、地址或薪資。
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportsTo", insertable = false, updatable = false)
    var manager: Employee? = null,

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    var subordinates: MutableList<Employee> = mutableListOf(),

    //------------------------------------------
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    var orders: MutableList<Order> = mutableListOf(),
)