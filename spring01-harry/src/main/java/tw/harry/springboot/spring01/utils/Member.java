package tw.harry.springboot.spring01.utils;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Member {

    private Integer id;

    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    private String account;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")
    private String passwd;

    @Past
    private LocalDate birthday;

    @FutureOrPresent
    private LocalDate pLeaveDate;

    @PositiveOrZero
    private Integer age;

    public String toString() {
        return "id: " + id;
    }
}
