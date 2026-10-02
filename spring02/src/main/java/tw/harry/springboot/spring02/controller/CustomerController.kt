package tw.harry.springboot.spring02.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring02.entity.Customer
import tw.harry.springboot.spring02.repository.CustomerRepo
import kotlin.jvm.optionals.getOrNull

@RequestMapping("/customers")
@RestController
class CustomerController(
    @Autowired
    val repo: CustomerRepo
) {

    @GetMapping("/v1/{id}")
    fun test1(@PathVariable id: String): ResponseEntity<Customer> {
        return ResponseEntity.ok(repo.findById(id).getOrNull())
    }

    @GetMapping("/v2/{id}")
    fun test2(@PathVariable id: String): ResponseEntity<Customer> {
        return ResponseEntity.ok(repo.findByCustomerId(id).getOrNull())
    }

    @GetMapping("/v3/{id}")
    fun test3(@PathVariable id: String): ResponseEntity<Customer> {
        return ResponseEntity.ok(repo.findByCustID(id).getOrNull())
    }

    @GetMapping("/v4/{id}")
    fun test4(@PathVariable id: String): ResponseEntity<Customer> {
        val opt = repo.findById(id)

        repo.findByCustID(id).orElse(Customer())

        if (opt.isPresent) {
            return ResponseEntity.ok(opt.get())
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}