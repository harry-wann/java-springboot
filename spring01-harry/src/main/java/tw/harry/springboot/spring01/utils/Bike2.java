package tw.harry.springboot.spring01.utils;

import org.springframework.stereotype.Component;

@Component
public class Bike2 implements Bike {
    @Override
    public void upSpeed() {
        System.out.println("Bike2:upSpeed()");
    }

    @Override
    public void downSpeed() {
        System.out.println("Bike2:downSpeed()");
    }
}
