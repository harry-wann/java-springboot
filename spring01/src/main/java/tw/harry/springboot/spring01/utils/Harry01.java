package tw.harry.springboot.spring01.utils;

import org.springframework.stereotype.Component;

/*
* IoC => 控制反轉 => Bean (物件)
* => @Component => 類別
* => @Bean => 方法 => return => Bean
* */

@Component
public class Harry01 {

    public Harry01() {
        System.out.println("Harry01()");
    }

    public Harry01(int a) {
        System.out.println("Harry01(int a)");
    }

    public void test1() {
        System.out.println("Harry01.test1()");
    }
}
