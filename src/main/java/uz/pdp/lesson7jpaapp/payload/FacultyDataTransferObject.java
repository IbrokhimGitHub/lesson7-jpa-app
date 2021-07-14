package uz.pdp.lesson7jpaapp.payload;

import lombok.Data;
import uz.pdp.lesson7jpaapp.entity.University;

@Data
public class FacultyDataTransferObject {
    private String name;
    private Integer universityId;
}
