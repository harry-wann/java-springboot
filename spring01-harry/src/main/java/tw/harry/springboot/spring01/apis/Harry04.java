package tw.harry.springboot.spring01.apis;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tw.harry.springboot.spring01.utils.Member;

@RestController
@RequestMapping("/member")
public class Harry04 {

    @PostMapping
    public void register(@RequestBody @Validated Member member) {
        System.out.println("register()");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        System.out.println("delete()" + id);
    }

    @PutMapping
    public void update(@RequestBody @Validated Member member) {
        System.out.println("update: " + member.toString());
    }

    @GetMapping
    public void findAll() {
        System.out.println("findAll");
    }

    @GetMapping("/{id}")
    public void findById(@PathVariable String id) {
        System.out.println("findById: " + id);
    }

    @GetMapping("/{name}/{gender}")
    public void findByNameAndGender(
        @PathVariable String name,
        @PathVariable String gender
    ) {
        System.out.println("findByNameAndGender: " + name);
    }
}
