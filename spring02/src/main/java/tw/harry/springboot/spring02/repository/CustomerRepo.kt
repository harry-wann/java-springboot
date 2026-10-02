package tw.harry.springboot.spring02.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring02.entity.Customer
import java.util.Optional

@Repository
interface CustomerRepo : JpaRepository<Customer, String> {

    @Query("""
        SELECT C
        FROM Customer C
        WHERE C.customerId = :id
    """)
    fun findByCustID(@Param("id") id: String): Optional<Customer>

    fun findByCustomerId(@Param("id") id: String): Optional<Customer>

    /*
    *
    * 動詞 + 介係詞(By) + 屬性名稱
    * ex:
    * findByCompanyName(companyName: String) => Optional<Customer> / Customer
    *
    * countByBirthday() => long
    * deleteByAccount(account: String) => void
    *
    * And/Or
    * findByGenderAndAge(gender: Boolean, age: Int) => List<>
    * findByGenderOrAge(gender: Boolean, age: Int) => List<>
    *
    * OrderBy + 屬性 + Asc/Desc
    * findByLastNameOrderByFirstNameAscAndTitleDesc
    *
    *
    * */
}