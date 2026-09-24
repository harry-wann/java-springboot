package tw.harry.springboot.spring01.utils;

import org.springframework.stereotype.Component;

@Component
public class Bike1 implements Bike {
    @Override
    public void upSpeed() {
        System.out.println("Bike1:upSpeed()");
    }

    @Override
    public void downSpeed() {
        System.out.println("Bike1:downSpeed()");
    }
}
