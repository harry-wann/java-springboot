package tw.harry.springboot.spring02.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring02.entity.Employee
import tw.harry.springboot.spring02.projection.EmployeeProjection
import tw.harry.springboot.spring02.repository.EmployeeRepo
import kotlin.jvm.optionals.getOrNull

@RequestMapping("/employees")
@RestController
class EmployeeController(
    @Autowired
    val repo: EmployeeRepo
) {

    @GetMapping("/v1/{id}")
    fun test1(@PathVariable id: Int): ResponseEntity<Employee> {
        return ResponseEntity.ok(repo.findById(id).getOrNull())
    }

    @GetMapping("/v2/{id}")
    fun test2(@PathVariable id: Int): ResponseEntity<EmployeeProjection> {
        val ep: EmployeeProjection = repo.searchByEmployeeId(id).orElse(null)
        return ResponseEntity.ok(ep)
    }
}