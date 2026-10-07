package tw.harry.springboot.spring04.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/my")
class MyController {

    @RequestMapping("/test1")
    fun test1(): ResponseEntity<String> {
        System.out.println("my:test1()")
        return ResponseEntity.ok("ok")
    }

    @RequestMapping("/test2")
    fun test2() {
        System.out.println("my:test2()")
    }

    @RequestMapping("/test3")
    fun test3() {
        System.out.println("my:test3()")
    }

}