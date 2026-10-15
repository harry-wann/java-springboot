package tw.harry.springboot.springai_db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import tw.harry.springboot.springai_db.entity.Customer

interface CustomerRepository : JpaRepository<Customer, String> {

    // JPQL 的 c 是 Customer 實體別名；select、where 與 order by 必須使用同一別名。
    @Query(
        """
        select c
        from Customer c 
        where lower(c.companyName) like :keyword  
        order by c.customerId
        """
    )
    fun search(@Param("keyword") keyword: String?): MutableList<Customer>

    /** 只有此查詢載入訂單；LEFT JOIN 保留沒有訂單的主體，distinct 避免重複主體。  */
    @Query(
        """
        select distinct c
        from Customer c
        left join fetch c.orders
        where lower(c.companyName) like :keyword
        order by c.customerId
        """
    )
    fun searchWithOrders(@Param("keyword") keyword: String?): MutableList<Customer>

}