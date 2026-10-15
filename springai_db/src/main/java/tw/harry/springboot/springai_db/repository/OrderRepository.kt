package tw.harry.springboot.springai_db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import tw.harry.springboot.springai_db.entity.Order

interface OrderRepository : JpaRepository<Order, Int> {

    @Query(
    """
        select o
        from Order o
        where o.employeeId = :employeeId
        order by o.orderId desc
        """
    )
    fun findByEmployee(
        @Param("employeeId") employeeId: Int?
    ): List<Order>

    /** 客戶代碼獨立查詢，不與員工條件混合。  */
    @Query(
    """
        select o
        from Order o
        where o.customerId = :customerId
        order by o.orderId desc
        """
    )
    fun findByCustomer(
        @Param("customerId") customerId: String?
    ): List<Order>
}