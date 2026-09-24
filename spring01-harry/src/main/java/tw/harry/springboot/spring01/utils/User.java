package tw.harry.springboot.spring01.utils;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String name;
    private Boolean gender;
    private Integer age;

    public String toString() {
        return id + ", " + name + ", " + gender + ", " + age;
    }
}
