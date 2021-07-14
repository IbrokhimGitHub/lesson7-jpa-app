package uz.pdp.lesson7jpaapp.payload;

import lombok.Data;

@Data
public class GroupDto {
    public String name;
    private Integer facultyId;
}
