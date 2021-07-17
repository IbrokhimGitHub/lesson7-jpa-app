package uz.pdp.lesson7jpaapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentDto {
    private String firstName;
    private String lastName;
    private Integer addressId;
    private Integer groupId;
    private List<Integer> subjectsId;

}
