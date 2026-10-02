package tw.harry.springboot.spring01.apis;

import org.springframework.web.bind.annotation.*;
import tw.harry.springboot.spring01.utils.User;

@RestController
@RequestMapping("/harry03")
public class Harry03 {

    // 403 權限
    // 404
    // 405 method
    @RequestMapping("/calc")
    public String calc(
        @RequestParam(required = false, defaultValue = "0") String x,
        @RequestParam(required = false, defaultValue = "0") String y
    ) {
        System.out.printf("x=%s,y=%s\n", x, y);

        try {
            return String.valueOf(Integer.parseInt(x) + Integer.parseInt(y));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("test1")
    public String test1(
        @RequestBody User user
    ) {
        System.out.println("TEST1");

        return user.toString();
    }

    @RequestMapping("test2/{name}/{id}")
    public String test2(
        @PathVariable String name,
        @PathVariable String id
    ) {

        return name+id;
    }

    @RequestMapping("test3")
    public String test3(
        @RequestHeader(name = "Content-type") String contentType,
        @RequestHeader String customXX
    ) {
        System.out.println("TEST3: " + contentType + ", " + customXX);
        return "ok " + contentType  + ", " + customXX;
    }

    @RequestMapping("/test4/{name}/{id}")
    public String test4(
        @RequestParam(name = "x", required = false, defaultValue = "0") String xx,
        @RequestParam(name = "y", required = false, defaultValue = "0") String yy,
        @RequestBody User user,
        @PathVariable String name,
        @PathVariable String id,
        @RequestHeader(name = "Content-type") String contentType,
        @RequestHeader String customXX
    ) {
        System.out.printf("xx=%s,yy=%s\n", xx,yy);
        System.out.println("User: " + user.toString());
        System.out.printf("name, id: %s, %s\n", name, id);
        System.out.println("Header: " + contentType + ", " + customXX);
        return "ok";
    }
}
