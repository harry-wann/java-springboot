package tw.harry.springboot.spring02.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tw.harry.springboot.spring02.entity.Employee
import tw.harry.springboot.spring02.projection.EmployeeProjection
import java.util.Optional

@Repository
interface EmployeeRepo : JpaRepository<Employee, Int> {
    fun searchByEmployeeId(id: Int): Optional<EmployeeProjection>
}