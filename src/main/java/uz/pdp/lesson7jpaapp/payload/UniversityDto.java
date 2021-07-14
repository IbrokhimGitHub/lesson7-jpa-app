package uz.pdp.lesson7jpaapp.payload;

import lombok.Data;

@Data

public class UniversityDto {//ma'lumotlarni tashish uchun xizmat qiluvchi class
    private String name;
    private String city;
    private String district;
    private String street;
}
