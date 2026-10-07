package tw.harry.springboot.spring04.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tw.harry.springboot.spring04.annotation.HarryAop

@RestController
@RequestMapping("/harry")
class HarryController {

    @RequestMapping("/test1")
    fun test1(): ResponseEntity<String> {
        System.out.println("harry:test1()")
        return ResponseEntity.ok("ok")
    }

    @HarryAop
    @RequestMapping("/test2")
    fun test2(a: Int) {
        System.out.println("harry:test2() ${a}")
        Thread.sleep(3 * 1000)
    }

    @HarryAop
    @RequestMapping("/test3")
    fun test3(b: Int, c: String) {
        System.out.println("harry:test3() ${b} ${c}")
    }

}