package tw.harry.springboot.spring01.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.harry.springboot.spring01.utils.Bike;
import tw.harry.springboot.spring01.utils.Harry01;

@RestController
@RequestMapping("/harry02")  // => Bean => Component
public class Harry02 {

    @Autowired
    private Harry01 harry01;

    @Autowired
    private Harry01 harry02;

    @Autowired
    private Bike bike1; // = new Bike1();

    @Autowired
    private Bike bike2; // = new Bike2()

    @Qualifier("bike1")
    @Autowired
    private Bike bike3;

    public Harry02() {
        System.out.println("Harry02()");
    }

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("test1()");
        harry01.test1();
        harry02.test1();
    }

    @RequestMapping("/test2")
    public String test2() {
        return "<h1>Harry</h1>";
    }

    @RequestMapping("/test3")
    public void test3() {
        bike1.upSpeed();
        bike1.downSpeed();
    }

    @RequestMapping("/test4")
    public void test4() {
        bike2.upSpeed();
        bike2.downSpeed();
    }

    @RequestMapping("/test5")
    public void test5() {
        bike3.upSpeed();
        bike3.downSpeed();
    }
}
