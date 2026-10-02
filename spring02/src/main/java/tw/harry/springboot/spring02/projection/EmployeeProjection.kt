package tw.harry.springboot.spring02.projection

/*
* Method name => Entity
*
* */
interface EmployeeProjection {
    fun getLastName(): String
    fun getFirstName(): String
    fun getTitle(): String
    fun getOrders(): List<OrderProjection>
}