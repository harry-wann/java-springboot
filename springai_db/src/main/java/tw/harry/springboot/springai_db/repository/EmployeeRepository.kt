package tw.harry.springboot.springai_db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import tw.harry.springboot.springai_db.entity.Employee

interface EmployeeRepository : JpaRepository<Employee, Int> {

    @Query("""
        select e 
        from Employee e 
        where lower(concat(e.firstName, ' ', e.lastName)) like :keyword 
        order by e.employeeId
        """)
    fun search(@Param("keyword") keyword: String): List<Employee>
    /** 只有此查詢載入訂單；LEFT JOIN 保留沒有訂單的主體，distinct 避免重複主體。 */

    @Query("""
        select distinct e
        from Employee e
        left join fetch e.orders
        where lower(concat(e.firstName, ' ', e.lastName)) like :keyword
        order by e.employeeId
        """)
    fun searchWithOrders(@Param("keyword") keyword: String): List<Employee>
}